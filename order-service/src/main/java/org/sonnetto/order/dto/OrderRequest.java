package org.sonnetto.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Status;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest implements Serializable {
    private Long userId;
    private Address address;
    private PurchaseRequest purchase;

    @Valid
    public Order toOrder() {
        return new Order(null, address, Status.NOT_CONFIRMED, purchase.toPurchase(), userId);
    }
}
