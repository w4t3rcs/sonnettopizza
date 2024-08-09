package org.sonnetto.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PriceResponse implements Serializable {
    private Float value;
    private String currency;
}