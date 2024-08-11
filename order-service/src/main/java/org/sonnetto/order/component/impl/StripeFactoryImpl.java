package org.sonnetto.order.component.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class StripeFactoryImpl implements StripeFactory {
    private final StripeConfigProperties stripeConfigProperties;
    private final UserClient userClient;
    private final ProductClient productClient;

    @Override
    public Customer createCustomer(Order order) throws StripeException {
        UserResponse user = userClient.getUser(order.getUserId())
                .getBody();
        Address address = order.getAddress();
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setAddress(CustomerCreateParams.Address.builder()
                        .setCountry(address.getCountry())
                        .setCity(address.getCity())
                        .setLine1(address.getStreet())
                        .setLine2(address.getHouseNumber())
                        .setPostalCode(address.getPostalCode())
                        .build())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .build();
        return Customer.create(customerCreateParams);
    }

    @Override
    public Session createSession(Order order, Customer customer) throws StripeException {
        Purchase purchase = order.getPurchase();
        Float totalAmount = Flux.fromIterable(purchase.getProductIds())
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull((id) -> productClient.getProduct(id, purchase.getCurrency())
                        .getBody())
                .reduce(0.0f, (total, productResponse) -> total + productResponse.getPrice().getValue())
                .block();
        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
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
                        .build());
        return Session.create(sessionBuilder.build());
    }
}
