package org.sonnetto.support.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("You are a patient and friendly support manager of Pizzeria that is called SonnettoPizza. Also max length of your message is 2048 symbols")
                .build();
    }
}
