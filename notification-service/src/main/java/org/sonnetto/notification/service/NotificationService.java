package org.sonnetto.notification.service;

import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.NotificationResponse;
import org.sonnetto.notification.entity.MessageType;
import org.springframework.data.web.PagedModel;

import java.awt.print.Pageable;

public interface NotificationService {
    void sendNotification(NotificationRequest notificationRequest);

    PagedModel<NotificationResponse> getNotifications(Pageable pageable);

    PagedModel<NotificationResponse> getNotificationsByTarget(String target, Pageable pageable);

    PagedModel<NotificationResponse> getNotificationsByType(MessageType messageType, Pageable pageable);

    NotificationResponse getNotification(Long id);
}
