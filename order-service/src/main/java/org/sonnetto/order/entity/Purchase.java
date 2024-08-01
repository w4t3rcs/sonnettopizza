package org.sonnetto.order.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sonnetto.order.validation.PriceIdList;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Purchase implements Serializable {
    @PriceIdList
    @ElementCollection
    private List<Long> priceIds;
    @Min(0)
    private Float summary;
    @NotBlank
    private String code;
    @Embedded
    private CreditCard creditCard;
}
