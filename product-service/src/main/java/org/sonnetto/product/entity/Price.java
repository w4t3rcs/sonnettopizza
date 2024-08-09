package org.sonnetto.product.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Embeddable
public class Price {
    @Min(0)
    private Float value;
    @NotBlank
    @Length(min = 3, max = 3)
    private String currency;
}
