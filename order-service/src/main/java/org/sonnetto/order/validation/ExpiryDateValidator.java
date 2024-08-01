package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpiryDateValidator implements ConstraintValidator<ExpiryDate, String> {
    @Override
    public boolean isValid(String expiryDate, ConstraintValidatorContext constraintValidatorContext) {
        return expiryDate != null && expiryDate.matches("^(0[1-9]|1[0-2])/(20([0-9]{2})|[0-9]{2})$");
    }
}
