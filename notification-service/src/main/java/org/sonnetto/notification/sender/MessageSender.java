package org.sonnetto.notification.sender;

import org.sonnetto.notification.document.Message;

public interface MessageSender {
    void send(Message message);
}
