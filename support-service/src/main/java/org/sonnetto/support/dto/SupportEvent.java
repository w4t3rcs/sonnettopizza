package org.sonnetto.support.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sonnetto.support.client.UserClient;
import org.sonnetto.support.config.ApplicationContextContainer;
import org.sonnetto.support.entity.SupportSession;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class SupportEvent implements Serializable {
    private String userName;
    private String userEmail;
    private String content;

    public static SupportEvent fromSupportSession(SupportSession supportSession) {
        UserClient userClient = ApplicationContextContainer.getApplicationContext()
                .getBean(UserClient.class);
        UserResponse userResponse = userClient.getUser(supportSession.getRequest()
                .getSenderId()).getBody();
        return new SupportEvent(userResponse.getName(), userResponse.getEmail(), supportSession.getResult());
    }
}
