package org.sonnetto.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.notification.dto.NotificationRequest;
import org.sonnetto.notification.dto.NotificationResponse;
import org.sonnetto.notification.entity.Notification;
import org.sonnetto.notification.exception.NotificationNotFoundException;
import org.sonnetto.notification.repository.NotificationRepository;
import org.sonnetto.notification.sender.MessageSender;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final MessageSender messageSender;

    @Override
    @Caching(cacheable = @Cacheable(value = "notification"))
    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = notificationRepository.save(notificationRequest.toNotification());
        messageSender.send(notification.getMessage());
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationResponse> getNotifications(Pageable pageable) {
        return new PagedModel<>(notificationRepository.findAll(pageable)
                .map(NotificationResponse::fromNotification));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationResponse> getNotificationsByTarget(String target, Pageable pageable) {
        return new PagedModel<>(notificationRepository.findAllByMessageTarget(target, pageable)
                .map(NotificationResponse::fromNotification));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationResponse> getNotificationsByBody(String body, Pageable pageable) {
        return new PagedModel<>(notificationRepository.findAllByMessageBody(body, pageable)
                .map(NotificationResponse::fromNotification));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationResponse> getNotificationsBySentDate(LocalDateTime sentDate, Pageable pageable) {
        return new PagedModel<>(notificationRepository.findAllBySentDate(sentDate, pageable)
                .map(NotificationResponse::fromNotification));
    }

    @Override
    @Cacheable(value = "notification")
    @Transactional(readOnly = true)
    public NotificationResponse getNotification(Long id) {
        return NotificationResponse.fromNotification(notificationRepository.findById(id)
                .orElseThrow(NotificationNotFoundException::new));
    }
}
