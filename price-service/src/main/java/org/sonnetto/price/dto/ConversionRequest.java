package org.sonnetto.price.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ConversionRequest implements Serializable {
    private float value;
    private String code;
    private String conversionCode;
}
