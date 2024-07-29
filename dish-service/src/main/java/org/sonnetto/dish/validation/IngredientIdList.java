package org.sonnetto.dish.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IngredientIdListValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IngredientIdList {
    String message() default "Invalid ingredient id list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
