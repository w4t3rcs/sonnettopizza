package org.sonnetto.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.order.config.ApplicationContextContainer;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Status;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
public class OrderEvent implements Serializable {
    private static final String USER_URI = "http://user-service:8081/api/v1.0/users/{id}";
    private Long id;
    private Status status;
    private String userName;
    private String userEmail;

    public static OrderEvent fromOrder(Order order) {
        WebClient webClient = ApplicationContextContainer.getApplicationContext().getBean(WebClient.class);
        UserResponse userResponse = Objects.requireNonNull(webClient.get()
                .uri(USER_URI)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block());
        return new OrderEvent(order.getId(), order.getStatus(), userResponse.getName(), userResponse.getEmail());
    }
}
