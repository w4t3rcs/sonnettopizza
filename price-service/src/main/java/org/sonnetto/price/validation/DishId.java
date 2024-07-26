package org.sonnetto.price.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DishIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DishId {
    String message() default "Invalid dish id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
