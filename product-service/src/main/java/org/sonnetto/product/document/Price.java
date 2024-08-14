package org.sonnetto.product.document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class Price implements Serializable {
    @Min(0)
    private Float value;
    @NotBlank
    @Length(min = 3, max = 3)
    private String currency;
}
