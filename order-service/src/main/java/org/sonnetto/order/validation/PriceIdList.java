package org.sonnetto.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceIdListValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceIdList {
    String message() default "Invalid price id list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
