package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
    String message() default "Invalid user id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
