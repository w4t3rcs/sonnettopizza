package org.sonnetto.support.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sonnetto.support.entity.Message;
import org.sonnetto.support.entity.SupportSession;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class SupportSessionRequest implements Serializable {
    private Message request;

    public SupportSession toSupportSession() {
        return new SupportSession(null, this.getRequest(), null);
    }
}
