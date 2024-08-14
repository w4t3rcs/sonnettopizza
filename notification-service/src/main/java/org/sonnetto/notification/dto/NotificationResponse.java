package org.sonnetto.notification.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.sonnetto.notification.document.Message;
import org.sonnetto.notification.document.Notification;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class NotificationResponse implements Serializable {
    private Long id;
    private Message message;
    private LocalDateTime sentDate;

    public static NotificationResponse fromNotification(@Valid Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .sentDate(notification.getSentDate())
                .build();
    }
}
