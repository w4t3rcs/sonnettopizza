package org.sonnetto.notification.service;

public interface UserConsumerService {
    void listenUserCreation(String name);

    void listenUserUpdate(String name);
}
