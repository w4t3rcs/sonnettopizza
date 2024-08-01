package org.sonnetto.order.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.sonnetto.order.validation.Cvc;
import org.sonnetto.order.validation.ExpiryDate;

@Data
@Embeddable
public class CreditCard {
    @CreditCardNumber
    private String number;
    @ExpiryDate
    private String expiryDate;
    @Cvc
    private String cvc;
}
