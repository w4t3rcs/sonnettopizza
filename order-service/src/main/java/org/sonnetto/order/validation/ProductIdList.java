package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductIdListValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductIdList {
    String message() default "Invalid product id list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
