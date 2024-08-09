package org.sonnetto.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.sonnetto.order.entity.Purchase;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseRequest implements Serializable {
    private List<Long> priceIds;
    private String currency;

    public Purchase toPurchase() {
        return new Purchase(this.getPriceIds(), null, this.getCurrency(), null);
    }
}
