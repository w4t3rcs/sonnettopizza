package org.sonnetto.support.dto;

import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.support.entity.Message;

@Data
public class MessageRequest {
    private Long senderId;
    private String content;

    @Valid
    public Message toMessage() {
        return new Message(this.getSenderId(), this.getContent());
    }
}
