package org.sonnetto.order.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.sonnetto.order.validation.ProductNameList;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Purchase implements Serializable {
    @ProductNameList
    @ElementCollection
    private List<String> productNames;
    @Min(0)
    private Float summary;
    @NotBlank
    @Length(min = 3, max = 3)
    private String currency;
    @NotBlank
    private String paymentUrl;
}
