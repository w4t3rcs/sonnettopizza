package org.sonnetto.support.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sonnetto.support.client.UserClient;
import org.sonnetto.support.config.ApplicationContextContainer;

public class MessageSenderIdValidator implements ConstraintValidator<MessageSenderId, Long> {
    private UserClient userClient;

    @Override
    public void initialize(MessageSenderId constraintAnnotation) {
        this.userClient = ApplicationContextContainer.getApplicationContext().getBean(UserClient.class);
    }

    @Override
    public boolean isValid(Long senderId, ConstraintValidatorContext constraintValidatorContext) {
        return userClient.getUserHead(senderId)
                .getStatusCode()
                .is2xxSuccessful();
    }
}
