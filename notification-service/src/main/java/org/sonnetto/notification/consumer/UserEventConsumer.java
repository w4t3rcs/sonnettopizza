package org.sonnetto.notification.consumer;

import lombok.RequiredArgsConstructor;
import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.UserEvent;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "user.created", groupId = "notification-group-id")
    public void processUserCreation(UserEvent userEvent) {
        String subject = "Successful account creation";
        String formattedBody = "Greetings, %s!\n\nWe're lucky to inform you about your account has been created successfully! Have an unforgettable experience with SonnettoPizza!\n\nSonnettoPizza"
                .formatted(userEvent.getName());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(userEvent.getEmail())
                .subject(subject)
                .body(formattedBody)
                .build();
        notificationService.sendNotification(notificationRequest);
    }

    @KafkaListener(topics = "user.updated", groupId = "notification-group-id")
    public void processUserUpdate(UserEvent userEvent) {
        String subject = "Successful account update";
        String formattedBody = "Greetings, %s!\n\nWe would like to inform that your account has been updated successfully! Have a great day with SonnettoPizza!\n\nSonnettoPizza"
                .formatted(userEvent.getName());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(userEvent.getEmail())
                .subject(subject)
                .body(formattedBody)
                .build();
        notificationService.sendNotification(notificationRequest);
    }
}