package org.sonnetto.support.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sonnetto.support.entity.SupportSession;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class SupportSessionResponse implements Serializable {
    private Long id;
    private String result;

    public static SupportSessionResponse fromSupportSession(SupportSession supportSession) {
        return new SupportSessionResponse(supportSession.getId(), supportSession.getResult());
    }
}
