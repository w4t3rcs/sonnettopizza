package org.sonnetto.order.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class Purchase implements Serializable {
    @Min(0)
    private Float summary;
    @NotBlank
    private String code;
}
