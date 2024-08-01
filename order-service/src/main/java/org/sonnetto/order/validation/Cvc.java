package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CvcValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cvc {
    String message() default "Invalid cvc";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
