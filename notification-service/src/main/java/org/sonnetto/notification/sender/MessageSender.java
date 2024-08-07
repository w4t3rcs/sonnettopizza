package org.sonnetto.notification.sender;

import org.sonnetto.notification.entity.Message;

public interface MessageSender {
    void send(Message message);
}
