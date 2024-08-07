package org.sonnetto.notification.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEvent implements Serializable {
    private String name;
    private String email;
}
