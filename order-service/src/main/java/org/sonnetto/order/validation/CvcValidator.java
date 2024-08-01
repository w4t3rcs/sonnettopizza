package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CvcValidator implements ConstraintValidator<Cvc, String> {
    @Override
    public boolean isValid(String cvc, ConstraintValidatorContext constraintValidatorContext) {
        return cvc != null && cvc.matches("^[0-9]{3,4}$");
    }
}
