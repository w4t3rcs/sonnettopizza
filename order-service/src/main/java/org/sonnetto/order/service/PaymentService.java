package org.sonnetto.order.service;

import org.sonnetto.order.entity.Order;

public interface PaymentService {
    void processPayment(Order order);
}
