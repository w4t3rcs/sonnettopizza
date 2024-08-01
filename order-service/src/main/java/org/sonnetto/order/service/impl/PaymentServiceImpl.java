package org.sonnetto.order.service.impl;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.sonnetto.order.dto.OrderRequest;
import org.sonnetto.order.dto.PriceResponse;
import org.sonnetto.order.dto.PurchaseRequest;
import org.sonnetto.order.dto.UserResponse;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Data
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final String USER_URI = "http://user-service:8081/api/v1.0/users/{id}";
    private static final String PRICE_URI = "http://price-service:8084/api/v1.0/prices/{id}?convert_to={code}";
    private final WebClient webClient;
    @Value("${stripe.api-key}")
    private String stripeApiKey;
    @Value("${stripe.client.success-url}")
    private String clientSuccessUrl;
    @Value("${stripe.client.failure-url}")
    private String clientFailureUrl;

    @Override
    @SneakyThrows
    public Float processPayment(OrderRequest orderRequest) {
        Stripe.apiKey = stripeApiKey;
        //Getting User from API
        UserResponse userResponse = webClient.get()
                .uri(USER_URI, orderRequest.getUserId())
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
        //Setting needed address
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
        Customer customer = Customer.create(customerCreateParams);

        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomer(customer.getId())
                .setSuccessUrl(clientSuccessUrl)
                .setCancelUrl(clientFailureUrl);

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
        Session session = Session.create(sessionBuilder.build());
        return session.getAmountTotal().floatValue();
    }
}
