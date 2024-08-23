package org.sonnetto.support.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.support.entity.Message;
import org.sonnetto.support.entity.SupportSession;
import org.sonnetto.support.service.AiChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {
    private final ChatClient chatClient;

    @Override
    public String respond(SupportSession supportSession) {
        Message requestMessage = supportSession.getRequest();
        return chatClient.prompt()
                .user(requestMessage.getContent())
                .call()
                .content();
    }
}
