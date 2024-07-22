package org.sonnetto.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.user.entity.Role;
import org.sonnetto.user.entity.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private String name;
    private String password;
    private String email;
    private Role role;

    @Valid
    public User toUser() {
        return User.builder()
                .name(this.getName())
                .password(this.getPassword())
                .email(this.getEmail())
                .role(this.getRole())
                .build();
    }
}
