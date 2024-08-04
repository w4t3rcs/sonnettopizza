package org.sonnetto.notification.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.sonnetto.notification.entity.MessageType;
import org.sonnetto.notification.entity.Notification;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class NotificationResponse implements Serializable {
    private Long id;
    private String target;
    private String message;
    private MessageType messageType;
    private LocalDateTime sentDate;

    public static NotificationResponse fromNotification(@Valid Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .target(notification.getTarget())
                .message(notification.getMessage())
                .messageType(notification.getMessageType())
                .sentDate(notification.getSentDate())
                .build();
    }
}
