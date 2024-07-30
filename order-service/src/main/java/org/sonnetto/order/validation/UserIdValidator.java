package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidatorContext;

public class UserIdValidator extends HttpResourceIdValidator<UserId, Long> {
    private static final String USER_ID = "http://price-service:8081/api/v1.0/users/{id}";

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return this.isResourceValid(USER_ID, id);
    }
}
