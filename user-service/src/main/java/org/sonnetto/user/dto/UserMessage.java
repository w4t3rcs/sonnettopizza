package org.sonnetto.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.user.entity.User;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserMessage implements Serializable {
    private String name;
    private String email;

    public static UserMessage fromUser(User user) {
        return new UserMessage(user.getName(), user.getEmail());
    }
}
