package org.sonnetto.support.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sonnetto.support.validation.MessageSenderId;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Message implements Serializable {
    @MessageSenderId
    private Long senderId;
    @NotBlank
    private String content;
}
