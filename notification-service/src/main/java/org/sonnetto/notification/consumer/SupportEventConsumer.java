package org.sonnetto.notification.consumer;

import lombok.RequiredArgsConstructor;
import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.SupportEvent;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportEventConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "support.created", groupId = "notification-group-id")
    public void processUserCreation(SupportEvent supportEvent) {
        String subject = "Notification from Support";
        String formattedBody = "Greetings, %s!\n\nYou have received a message from our \"AI Support Service\"!\nMessage: %s\n\nHave an unforgettable experience with SonnettoPizza!\n\nSonnettoPizza"
                .formatted(supportEvent.getUserName(), supportEvent.getContent());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(supportEvent.getUserEmail())
                .subject(subject)
                .body(formattedBody)
                .build();
        notificationService.sendNotification(notificationRequest);
    }
}