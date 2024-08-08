package org.sonnetto.notification.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEvent implements Serializable {
    private Long id;
    private OrderStatus status;
    private String userName;
    private String userEmail;
}
