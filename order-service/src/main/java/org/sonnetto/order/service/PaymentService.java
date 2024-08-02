package org.sonnetto.order.service;

import org.sonnetto.order.dto.OrderRequest;
import org.sonnetto.order.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(OrderRequest orderRequest);
}
