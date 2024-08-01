package org.sonnetto.order.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.sonnetto.order.validation.HouseNumber;
import org.sonnetto.order.validation.PostalCode;

import java.io.Serializable;

@Data
@Embeddable
public class Address implements Serializable {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @HouseNumber
    private String houseNumber;
    @PostalCode
    private String postalCode;
}
