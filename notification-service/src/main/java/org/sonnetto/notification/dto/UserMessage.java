package org.sonnetto.notification.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMessage implements Serializable {
    private String name;
    private String email;
}
