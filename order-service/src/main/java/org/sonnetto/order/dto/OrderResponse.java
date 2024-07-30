package org.sonnetto.order.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Purchase;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse implements Serializable {
    private Long id;
    private Long userId;
    private Address address;
    private List<Long> priceIds;
    private Purchase purchase;

    public static OrderResponse fromOrder(@Valid Order order) {
        return new OrderResponse(order.getId(), order.getUserId(), order.getAddress(), order.getPriceIds(), order.getPurchase());
    }
}
