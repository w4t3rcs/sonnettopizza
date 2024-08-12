package org.sonnetto.notification.repository;

import org.sonnetto.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;

public interface NotificationRepository extends ElasticsearchRepository<Notification, Long> {
    Page<Notification> findAllByMessageTarget(String target, Pageable pageable);

    Page<Notification> findAllByMessageBody(String body, Pageable pageable);

    Page<Notification> findAllBySentDate(LocalDateTime sentDate, Pageable pageable);
}
