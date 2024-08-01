package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {
    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext constraintValidatorContext) {
        return postalCode != null && postalCode.matches("(?i)^[a-z0-9][a-z0-9\\- ]{0,10}[a-z0-9]$");
    }
}
