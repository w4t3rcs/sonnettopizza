package org.sonnetto.notification.entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Message implements Serializable {
    private String target;
    private String subject;
    private String body;
}
