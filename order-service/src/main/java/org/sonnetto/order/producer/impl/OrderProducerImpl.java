package org.sonnetto.order.producer.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.order.dto.OrderEvent;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.producer.OrderProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducerImpl implements OrderProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Override
    public void sendOrder(String topic, Order order) {
        OrderEvent orderEvent = OrderEvent.fromOrder(order);
        kafkaTemplate.send(topic, orderEvent);
    }
}
