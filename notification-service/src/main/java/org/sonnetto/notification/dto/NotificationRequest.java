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
public class NotificationRequest implements Serializable {
    private String target;
    private String message;
    private MessageType messageType;

    @Valid
    public Notification toNotification() {
        return new Notification(null, this.getTarget(), this.getMessage(), this.getMessageType(), LocalDateTime.now());
    }
}
