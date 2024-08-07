package org.sonnetto.notification.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.UserEvent;
import org.sonnetto.notification.entity.Message;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "user.created", groupId = "notification-group-id")
    public void listenUserCreation(UserEvent userEvent) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(userEvent.getEmail())
                .subject("Successful account creation")
                .body("Greetings %s!\n\nWe're lucky to inform you about your account has been created successfully! Have an unforgettable experience with SonnettoPizza!\n\nSonnettoPizza"
                        .formatted(userEvent.getName()))
                .messageType(Message.Type.USER_CREATED)
                .build();
        notificationService.sendNotification(notificationRequest);
    }

    @KafkaListener(topics = "user.updated", groupId = "notification-group-id")
    public void listenUserUpdate(UserEvent userEvent) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(userEvent.getEmail())
                .subject("Successful account update")
                .body("Greetings %s!\n\nWe would like to inform that your account has been updated successfully! Have a great day with SonnettoPizza!\n\nSonnettoPizza"
                        .formatted(userEvent.getName()))
                .messageType(Message.Type.USER_UPDATED)
                .build();
        notificationService.sendNotification(notificationRequest);
    }
}