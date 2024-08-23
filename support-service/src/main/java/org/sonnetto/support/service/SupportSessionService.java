package org.sonnetto.support.service;

import org.sonnetto.support.dto.SupportSessionRequest;
import org.sonnetto.support.dto.SupportSessionResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface SupportSessionService {
    SupportSessionResponse createSupportSession(SupportSessionRequest supportSessionRequest);

    PagedModel<SupportSessionResponse> getSupportSessions(Pageable pageable);

    SupportSessionResponse getSupportSession(Long id);
}
