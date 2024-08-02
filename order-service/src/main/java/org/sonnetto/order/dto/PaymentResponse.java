package org.sonnetto.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PaymentResponse implements Serializable {
    private String url;
    private Float summary;
}
