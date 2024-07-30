package org.sonnetto.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Purchase;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest implements Serializable {
    private Long userId;
    private Address address;
    private List<Long> priceIds;
    private Purchase purchase;

    @Valid
    public Order toOrder() {
        return new Order(null, userId, address, priceIds, purchase);
    }
}
