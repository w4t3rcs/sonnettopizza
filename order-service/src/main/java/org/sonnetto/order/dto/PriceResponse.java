package org.sonnetto.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PriceResponse implements Serializable {
    private Long id;
    private Float value;
    private String code;
    private Long dishId;
}
