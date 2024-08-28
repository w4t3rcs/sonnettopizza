package org.sonnetto.support.producer.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.support.dto.SupportEvent;
import org.sonnetto.support.entity.SupportSession;
import org.sonnetto.support.producer.SupportSessionProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportSessionProducerImpl implements SupportSessionProducer {
    private final KafkaTemplate<String, SupportEvent> kafkaTemplate;

    @Override
    public void sendSupportSession(String topic, SupportSession supportSession) {
        SupportEvent supportEvent = SupportEvent.fromSupportSession(supportSession);
        kafkaTemplate.send(topic, supportEvent);
    }
}
