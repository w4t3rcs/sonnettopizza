package org.sonnetto.price.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversionResponse implements Serializable {
    @JsonProperty("base_code")
    private String code;
    @JsonProperty("conversion_rate")
    private float conversionRate;
}
