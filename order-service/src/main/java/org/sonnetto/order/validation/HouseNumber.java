package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HouseNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HouseNumber {
    String message() default "Invalid house number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
