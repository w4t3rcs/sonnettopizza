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
public class NotificationRequest implements Serializable {
    private String subject;
    private String target;
    private String body;

    @Valid
    public Notification toNotification() {
        return new Notification(null, new Message(this.getTarget(), this.getSubject(), this.getBody()), LocalDateTime.now());
    }
}
