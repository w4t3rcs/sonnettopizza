package org.sonnetto.notification.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Message implements Serializable {
    @NotBlank
    private String target;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        USER_CREATED,
        USER_UPDATED,
        ORDER_CREATED,
        ORDER_UPDATED
    }
}
