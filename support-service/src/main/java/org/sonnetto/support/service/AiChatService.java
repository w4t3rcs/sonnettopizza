package org.sonnetto.support.service;

import org.sonnetto.support.entity.SupportSession;

public interface AiChatService {
    String respond(SupportSession supportSession);
}
