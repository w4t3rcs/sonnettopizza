package org.sonnetto.order.component.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.sonnetto.order.component.StripeFactory;
import org.sonnetto.order.config.StripeConfigProperties;
import org.sonnetto.order.dto.PriceResponse;
import org.sonnetto.order.dto.UserResponse;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Purchase;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StripeFactoryImpl implements StripeFactory {
    private static final String USER_URI = "http://user-service:8081/api/v1.0/users/{id}";
    private static final String PRICE_URI = "http://price-service:8084/api/v1.0/prices/{id}?convert_to={code}";
    private final StripeConfigProperties stripeConfigProperties;
    private final WebClient webClient;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeConfigProperties.getApiKey();
    }

    @Override
    public Customer createCustomer(Order order) throws StripeException {
        UserResponse userResponse = Objects.requireNonNull(webClient.get()
                .uri(USER_URI, order.getUserId())
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block());
        Address address = order.getAddress();
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

    @Override
    public Session createSession(Order order, Customer customer) throws StripeException {
        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomer(customer.getId())
                .setSuccessUrl(stripeConfigProperties.getClient().getSuccessUrl())
                .setCancelUrl(stripeConfigProperties.getClient().getFailureUrl());
        Purchase purchase = order.getPurchase();
        Flux.fromIterable(purchase.getPriceIds())
                .flatMap((id) -> webClient.get()
                        .uri(PRICE_URI, id, purchase.getCode())
                        .retrieve()
                        .bodyToMono(PriceResponse.class)
                        .map(priceResponse -> SessionCreateParams.LineItem.PriceData.builder()
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .putMetadata("app_id", priceResponse.getId().toString())
                                        .setName(priceResponse.getDishId().toString())
                                        .build())
                                .setCurrency(purchase.getCode())
                                .setUnitAmount((long) (priceResponse.getValue() * 100))
                                .build()))
                .map(priceData -> SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build())
                .subscribe(sessionBuilder::addLineItem);
        return Session.create(sessionBuilder.build());
    }
}
