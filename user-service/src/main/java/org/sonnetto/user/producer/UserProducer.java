package org.sonnetto.user.producer;

import org.sonnetto.user.entity.User;

public interface UserProducer {
    void sendUser(String topic, User user);
}
