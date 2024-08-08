package org.sonnetto.order.producer;

import org.sonnetto.order.entity.Order;

public interface OrderProducer {
    void sendOrder(String topic, Order order);
}
