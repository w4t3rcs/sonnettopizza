package org.sonnetto.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.NotificationResponse;
import org.sonnetto.notification.entity.MessageType;
import org.sonnetto.notification.repository.NotificationRepository;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(NotificationRequest notificationRequest) {

    }

    @Override
    public PagedModel<NotificationResponse> getNotifications(Pageable pageable) {
        return null;
    }

    @Override
    public PagedModel<NotificationResponse> getNotificationsByTarget(String target, Pageable pageable) {
        return null;
    }

    @Override
    public PagedModel<NotificationResponse> getNotificationsByType(MessageType messageType, Pageable pageable) {
        return null;
    }

    @Override
    public NotificationResponse getNotification(Long id) {
        return null;
    }
}
