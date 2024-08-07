package org.sonnetto.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.user.entity.User;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserEvent implements Serializable {
    private String name;
    private String email;

    public static UserEvent fromUser(User user) {
        return new UserEvent(user.getName(), user.getEmail());
    }
}
