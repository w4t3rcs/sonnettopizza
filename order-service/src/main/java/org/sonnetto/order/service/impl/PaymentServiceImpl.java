package org.sonnetto.order.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.order.component.StripeFactory;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Purchase;
import org.sonnetto.order.exception.PaymentException;
import org.sonnetto.order.service.PaymentService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final StripeFactory stripeFactory;

    @Override
    public void processPayment(Order order) {
        try {
            Customer customer = stripeFactory.createCustomer(order);
            log.info("Customer created: {}", customer.getId());
            Session session = stripeFactory.createSession(order, customer);
            log.info("Session created: {}", session.getId());
            Purchase purchase = order.getPurchase();
            purchase.setPaymentUrl(session.getUrl());
            purchase.setSummary(session.getAmountTotal().floatValue());
        } catch (StripeException e) {
            log.error(e.getMessage());
            throw new PaymentException(e);
        }
    }
}
