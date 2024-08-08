package org.sonnetto.user.producer.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.user.dto.UserEvent;
import org.sonnetto.user.entity.User;
import org.sonnetto.user.producer.UserProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducerImpl implements UserProducer {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Override
    public void sendUser(String topic, User user) {
        UserEvent userEvent = UserEvent.fromUser(user);
        kafkaTemplate.send(topic, userEvent);
    }
}
