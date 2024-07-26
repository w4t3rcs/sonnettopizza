package org.sonnetto.price.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.price.entity.Price;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceRequest implements Serializable {
    private Float value;
    private String code;
    private Long dishId;

    @Valid
    public Price toPrice() {
        return new Price(null, this.getValue(), this.getCode(), this.getDishId());
    }
}
