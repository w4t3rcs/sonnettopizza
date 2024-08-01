package org.sonnetto.order.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Status;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderResponse implements Serializable {
    private Long id;
    private Address address;
    private Status status;
    private PurchaseResponse purchase;
    private Long userId;

    public static OrderResponse fromOrder(@Valid Order order) {
        return new OrderResponse(order.getId(), order.getAddress(), order.getStatus(), PurchaseResponse.fromPurchase(order.getPurchase()), order.getUserId());
    }
}
