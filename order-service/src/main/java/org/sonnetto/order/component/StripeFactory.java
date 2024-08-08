package org.sonnetto.order.component;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import org.sonnetto.order.entity.Order;

public interface StripeFactory {
    Customer createCustomer(Order order) throws StripeException;

    Session createSession(Order order, Customer customer) throws StripeException;
}
