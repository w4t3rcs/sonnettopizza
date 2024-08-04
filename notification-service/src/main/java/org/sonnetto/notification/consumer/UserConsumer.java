package org.sonnetto.notification.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.UserMessage;
import org.sonnetto.notification.entity.MessageType;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "user.created", groupId = "notification-group-id")
    public void listenUserCreation(UserMessage userMessage) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(userMessage.getEmail())
                .message("Greetings %s!\n\nWe're lucky to inform you about your account has been created successfully! Have a great day with SonnettoPizza!\n\nSonnettoPizza"
                        .formatted(userMessage.getName()))
                .messageType(MessageType.USER_CREATED)
                .build();
    }

    @KafkaListener(topics = "user.updated", groupId = "notification-group-id")
    public void listenUserUpdate(UserMessage userMessage) {
        log.info("User updated: {}", userMessage);
    }
}