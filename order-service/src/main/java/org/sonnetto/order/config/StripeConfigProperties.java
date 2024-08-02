package org.sonnetto.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("stripe")
public class StripeConfigProperties {
    private String apiKey;
    private Client client;

    @Data
    public static class Client {
        private String successUrl;
        private String failureUrl;
    }
}
