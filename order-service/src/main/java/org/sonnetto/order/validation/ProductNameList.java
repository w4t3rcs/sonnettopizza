package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductNameListValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductNameList {
    String message() default "Invalid product name list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
