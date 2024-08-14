package org.sonnetto.order.component.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.order.client.ProductClient;
import org.sonnetto.order.client.UserClient;
import org.sonnetto.order.component.StripeFactory;
import org.sonnetto.order.config.StripeConfigProperties;
import org.sonnetto.order.dto.UserResponse;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Purchase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StripeFactoryImpl implements StripeFactory {
    private final StripeConfigProperties stripeConfigProperties;
    private final UserClient userClient;
    private final ProductClient productClient;

    @Override
    public Customer createCustomer(Order order) {
        try {
            CompletableFuture<CustomerCreateParams> customerParams = this.createCustomerParams(order);
            return Customer.create(customerParams.get());
        } catch (StripeException | ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @CircuitBreaker(name = "user-service")
    @Retry(name = "user-service")
    @TimeLimiter(name = "user-service")
    private CompletableFuture<CustomerCreateParams> createCustomerParams(Order order) {
        UserResponse user = userClient.getUser(order.getUserId())
                .getBody();
        Address address = order.getAddress();
        return CompletableFuture.supplyAsync(() -> CustomerCreateParams.builder()
                .setAddress(CustomerCreateParams.Address.builder()
                        .setCountry(address.getCountry())
                        .setCity(address.getCity())
                        .setLine1(address.getStreet())
                        .setLine2(address.getHouseNumber())
                        .setPostalCode(address.getPostalCode())
                        .build())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .build());
    }

    @Override
    public Session createSession(Order order, Customer customer) {
        try {
            CompletableFuture<SessionCreateParams> sessionParams = this.createSessionParams(order, customer);
            return Session.create(sessionParams.get());
        } catch (StripeException | ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @CircuitBreaker(name = "product-service")
    @Retry(name = "product-service")
    @TimeLimiter(name = "product-service")
    private CompletableFuture<SessionCreateParams> createSessionParams(Order order, Customer customer) {
        Purchase purchase = order.getPurchase();
        Float totalAmount = Flux.fromIterable(purchase.getProductNames())
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull((name) -> productClient.getProduct(name, purchase.getCurrency())
                        .getBody())
                .reduce(0.0f, (total, productResponse) -> total + productResponse.getPrice().getValue())
                .block();
        return CompletableFuture.supplyAsync(() -> SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setCustomer(customer.getId())
                .setSuccessUrl(stripeConfigProperties.getClient().getSuccessUrl() + "/" + order.getId())
                .setCancelUrl(stripeConfigProperties.getClient().getFailureUrl())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("SonnettoPizza")
                                        .build())
                                .setCurrency(purchase.getCurrency())
                                .setUnitAmount((long) (Objects.requireNonNull(totalAmount) * 100))
                                .build())
                        .build())
                .build());
    }


}
