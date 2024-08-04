package org.sonnetto.notification.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sonnetto.notification.service.UserConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserConsumerServiceImpl implements UserConsumerService {
    @Override
    @KafkaListener(topics = "user.created", groupId = "notification-group-id")
    public void listenUserCreation(String name) {
        log.info("User created: {}", name);
    }

    @Override
    @KafkaListener(topics = "user.updated", groupId = "notification-group-id")
    public void listenUserUpdate(String name) {
        log.info("User updated: {}", name);
    }
}