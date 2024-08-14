package org.sonnetto.notification.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Message implements Serializable {
    private String target;
    private String subject;
    private String body;
}
