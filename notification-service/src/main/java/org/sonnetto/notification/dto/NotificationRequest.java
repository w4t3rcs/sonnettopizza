package org.sonnetto.notification.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.sonnetto.notification.entity.Message;
import org.sonnetto.notification.entity.Notification;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class NotificationRequest implements Serializable {
    private String subject;
    private String target;
    private String body;

    @Valid
    public Notification toNotification() {
        return new Notification(null, new Message(this.getTarget(), this.getSubject(), this.getBody()), LocalDateTime.now());
    }
}
