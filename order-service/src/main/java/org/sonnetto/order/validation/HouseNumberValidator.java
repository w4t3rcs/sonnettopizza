package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HouseNumberValidator implements ConstraintValidator<HouseNumber, String> {
    @Override
    public boolean isValid(String house, ConstraintValidatorContext constraintValidatorContext) {
        return house != null && house.matches("^[1-9]\\d*[a-z]?(?: ?([/-] ?\\d+[a-z]?))?$");
    }
}
