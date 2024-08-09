package org.sonnetto.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private PriceResponse price;
}
