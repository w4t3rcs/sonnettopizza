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
    private Address address;
    private Status status;
    private PurchaseRequest purchase;
    private Long userId;

    @Valid
    public Order toOrder() {
        return new Order(null, this.getAddress(), this.getStatus(), this.getPurchase().toPurchase(), this.getUserId());
    }
}
