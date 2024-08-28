package org.sonnetto.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.order.client.UserClient;
import org.sonnetto.order.config.ApplicationContextContainer;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Status;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderEvent implements Serializable {
    private Long id;
    private Status status;
    private String userName;
    private String userEmail;

    public static OrderEvent fromOrder(Order order) {
        UserClient userClient = ApplicationContextContainer.getApplicationContext()
                .getBean(UserClient.class);
        UserResponse userResponse = userClient.getUser(order.getUserId())
                .getBody();
        return new OrderEvent(order.getId(), order.getStatus(), userResponse.getName(), userResponse.getEmail());
    }
}
