package org.sonnetto.notification.repository;

import org.sonnetto.notification.entity.Message;
import org.sonnetto.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAllByMessageTarget(String target, Pageable pageable);

    Page<Notification> findAllByMessageType(Message.Type type, Pageable pageable);
}
