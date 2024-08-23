package org.sonnetto.support.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MessageSenderIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageSenderId {
    String message() default "Invalid message sender id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
