package org.sonnetto.order.component.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.order.component.StripeFactory;
import org.sonnetto.order.config.StripeConfigProperties;
import org.sonnetto.order.dto.ProductResponse;
import org.sonnetto.order.dto.UserResponse;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Purchase;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StripeFactoryImpl implements StripeFactory {
    private static final String USER_URI = "http://user-service:8081/api/v1.0/users/{id}";
    private static final String PRODUCT_URI = "http://product-service:8083/api/v1.0/products/{id}?currency={currency}";
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
        log.info(userResponse.toString());
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
        log.info(customerCreateParams.toString());
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
        Flux.fromIterable(purchase.getProductIds())
                .flatMap((id) -> webClient.get()
                        .uri(PRODUCT_URI, id, purchase.getCurrency())
                        .retrieve()
                        .bodyToMono(ProductResponse.class)
                        .map(productResponse -> SessionCreateParams.LineItem.PriceData.builder()
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .putMetadata("app_id", productResponse.getId().toString())
                                        .setName(productResponse.getName())
                                        .build())
                                .setCurrency(purchase.getCurrency())
                                .setUnitAmount((long) (productResponse.getPrice().getValue() * 100))
                                .build()))
                .map(priceData -> SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build())
                .subscribe(sessionBuilder::addLineItem);
        log.info(sessionBuilder.toString());
        return Session.create(sessionBuilder.build());
    }
}
