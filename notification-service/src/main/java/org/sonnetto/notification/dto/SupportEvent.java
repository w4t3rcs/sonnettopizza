package org.sonnetto.notification.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupportEvent implements Serializable {
    private String userName;
    private String userEmail;
    private String content;
}
