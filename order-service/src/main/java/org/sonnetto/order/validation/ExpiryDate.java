package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CvcValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpiryDate {
    String message() default "Invalid expiry date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
