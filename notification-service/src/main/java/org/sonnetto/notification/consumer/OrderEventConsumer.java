package org.sonnetto.notification.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.OrderEvent;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "order.created", groupId = "notification-group-id")
    public void processOrderCreation(OrderEvent orderEvent) {
        String subject = "Successful order placement";
        String formattedBody = "Greetings, %s!\n\nWe're lucky to inform you that order with id of %s has been placed successfully!\nThe status of your order is: %s!\n Best regards!\n\nSonnettoPizza"
                .formatted(orderEvent.getUserName(), orderEvent.getId(), orderEvent.getStatus());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(orderEvent.getUserEmail())
                .subject(subject)
                .body(formattedBody)
                .build();
        notificationService.sendNotification(notificationRequest);
    }

    @KafkaListener(topics = "order.updated", groupId = "notification-group-id")
    public void processOrderUpdate(OrderEvent orderEvent) {
        String subject = "Successful order placement";
        String formattedBody = "Greetings, %s!\n\nYour order with id of %s has been updated!\nThe status of your order is: %s!\n Warm regards!\n\nSonnettoPizza"
                .formatted(orderEvent.getUserName(), orderEvent.getId(), orderEvent.getStatus());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .target(orderEvent.getUserEmail())
                .subject(subject)
                .body(formattedBody)
                .build();
        notificationService.sendNotification(notificationRequest);
    }
}