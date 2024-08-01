package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PostalCodeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostalCode {
    String message() default "Invalid postal code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
