package org.sonnetto.notification.service;

import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.NotificationResponse;
import org.sonnetto.notification.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface NotificationService {
    void sendNotification(NotificationRequest notificationRequest);

    PagedModel<NotificationResponse> getNotifications(Pageable pageable);

    PagedModel<NotificationResponse> getNotificationsByTarget(String target, Pageable pageable);

    PagedModel<NotificationResponse> getNotificationsByType(Message.Type messageType, Pageable pageable);

    NotificationResponse getNotification(Long id);
}
