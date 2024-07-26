package org.sonnetto.price.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.price.entity.Price;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PriceResponse implements Serializable {
    private Long id;
    private Float value;
    private String code;
    private Long dishId;

    public static PriceResponse fromPrice(@Valid Price price) {
        return new PriceResponse(price.getId(), price.getValue(), price.getCode(), price.getDishId());
    }
}
