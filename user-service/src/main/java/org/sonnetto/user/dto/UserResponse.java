package org.sonnetto.user.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.sonnetto.user.entity.Role;
import org.sonnetto.user.entity.User;

import java.io.Serializable;

@Builder
@Data
public class UserResponse implements Serializable {
    private Long id;
    private String name;
    private String email;
    private Role role;

    public static UserResponse fromUser(@Valid User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
