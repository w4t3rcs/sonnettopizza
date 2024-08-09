package org.sonnetto.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sonnetto.order.entity.Purchase;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseResponse implements Serializable {
    private List<Long> priceIds;
    private Float summary;
    private String currency;

    public static PurchaseResponse fromPurchase(Purchase purchase) {
        return new PurchaseResponse(purchase.getPriceIds(), purchase.getSummary(), purchase.getCurrency());
    }
}
