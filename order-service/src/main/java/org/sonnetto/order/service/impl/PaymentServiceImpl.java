package org.sonnetto.order.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.sonnetto.order.config.StripeConfigProperties;
import org.sonnetto.order.dto.*;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.exception.PaymentException;
import org.sonnetto.order.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final String USER_URI = "http://user-service:8081/api/v1.0/users/{id}";
    private static final String PRICE_URI = "http://price-service:8084/api/v1.0/prices/{id}?convert_to={code}";
    private final WebClient webClient;
    private final StripeConfigProperties stripeConfigProperties;

    @Override
    public PaymentResponse processPayment(OrderRequest orderRequest) {
        Stripe.apiKey = stripeConfigProperties.getApiKey();
        try {
            Session session = createSession(orderRequest);
            return new PaymentResponse(session.getUrl(), session.getAmountTotal().floatValue());
        } catch (StripeException e) {
            throw new PaymentException(e);
        }
    }

    private Session createSession(OrderRequest orderRequest) throws StripeException {
        Customer customer = createCustomer(orderRequest);
        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomer(customer.getId())
                .setSuccessUrl(stripeConfigProperties.getClient().getSuccessUrl())
                .setCancelUrl(stripeConfigProperties.getClient().getFailureUrl());
        configureSessionBuilderByOrder(orderRequest, sessionBuilder);
        return Session.create(sessionBuilder.build());
    }

    private Customer createCustomer(OrderRequest orderRequest) throws StripeException {
        UserResponse userResponse = getUser(orderRequest);
        Address address = orderRequest.getAddress();
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setAddress(CustomerCreateParams.Address.builder()
                        .setCountry(address.getCountry())
                        .setCity(address.getCity())
                        .setLine1(address.getStreet())
                        .setLine2(address.getHouseNumber())
                        .setPostalCode(address.getPostalCode())
                        .build())
                .setName(userResponse.getName())
                .setEmail(userResponse.getEmail())
                .build();
        return Customer.create(customerCreateParams);
    }

    private UserResponse getUser(OrderRequest orderRequest) {
        return webClient.get()
                .uri(USER_URI, orderRequest.getUserId())
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }

    private void configureSessionBuilderByOrder(OrderRequest orderRequest, SessionCreateParams.Builder sessionBuilder) {
        PurchaseRequest purchaseRequest = orderRequest.getPurchase();
        Flux.fromIterable(purchaseRequest.getPriceIds())
                .flatMap((id) -> webClient.get()
                        .uri(PRICE_URI, id, purchaseRequest.getCode())
                        .retrieve()
                        .bodyToMono(PriceResponse.class)
                        .map(priceResponse -> SessionCreateParams.LineItem.PriceData.builder()
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .putMetadata("app_id", priceResponse.getId().toString())
                                        .setName(priceResponse.getDishId().toString())
                                        .build())
                                .setCurrency(purchaseRequest.getCode())
                                .setUnitAmount((long) (priceResponse.getValue() * 100))
                                .build()))
                .map(priceData -> SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build())
                .subscribe(sessionBuilder::addLineItem);
    }
}
