package org.sonnetto.user.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic userCreationTopic() {
        return TopicBuilder.name("user-creation-topic").build();
    }

    @Bean
    public NewTopic userUpdateTopic() {
        return TopicBuilder.name("user-update-topic").build();
    }
}