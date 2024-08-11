package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sonnetto.order.client.UserClient;
import org.sonnetto.order.config.ApplicationContextContainer;

public class UserIdValidator implements ConstraintValidator<UserId, Long> {
    private UserClient userClient;

    @Override
    public void initialize(UserId constraintAnnotation) {
        this.userClient = ApplicationContextContainer.getApplicationContext().getBean(UserClient.class);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return userClient.getUser(id)
                .getStatusCode()
                .is2xxSuccessful();
    }
}
