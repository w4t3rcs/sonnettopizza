package org.sonnetto.order.service;

import org.sonnetto.order.dto.OrderRequest;

public interface PaymentService {
    Float processPayment(OrderRequest orderRequest);
}
