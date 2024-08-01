package org.sonnetto.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponse implements Serializable {
    private Long id;
    private String name;
    private String email;
}
