package org.sonnetto.notification.service;

import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.NotificationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.time.LocalDateTime;

public interface NotificationService {
    void sendNotification(NotificationRequest notificationRequest);

    PagedModel<NotificationResponse> getNotifications(Pageable pageable);

    PagedModel<NotificationResponse> getNotificationsByTarget(String target, Pageable pageable);

    PagedModel<NotificationResponse> getNotificationsByBody(String body, Pageable pageable);

    PagedModel<NotificationResponse> getNotificationsBySentDate(LocalDateTime sentDate, Pageable pageable);

    NotificationResponse getNotification(Long id);
}
