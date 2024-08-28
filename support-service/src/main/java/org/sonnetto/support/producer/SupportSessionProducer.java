package org.sonnetto.support.producer;

import org.sonnetto.support.entity.SupportSession;

public interface SupportSessionProducer {
    void sendSupportSession(String topic, SupportSession supportSession);
}
