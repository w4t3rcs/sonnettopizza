package org.sonnetto.support.dto;

import jakarta.validation.Valid;
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

    public static SupportSessionResponse fromSupportSession(@Valid SupportSession supportSession) {
        return new SupportSessionResponse(supportSession.getId(), supportSession.getResult());
    }
}
