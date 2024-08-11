package org.sonnetto.order.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("stripe")
public class StripeConfigProperties {
    private String apiKey;
    private Client client;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.getApiKey();
    }

    @Data
    public static class Client {
        private String successUrl;
        private String failureUrl;
    }
}
