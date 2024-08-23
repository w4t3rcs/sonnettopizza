package org.sonnetto.support.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.support.dto.SupportSessionRequest;
import org.sonnetto.support.dto.SupportSessionResponse;
import org.sonnetto.support.entity.SupportSession;
import org.sonnetto.support.exception.SupportSessionNotFoundException;
import org.sonnetto.support.repository.SupportSessionRepository;
import org.sonnetto.support.service.AiChatService;
import org.sonnetto.support.service.SupportSessionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportSessionServiceImpl implements SupportSessionService {
    private final SupportSessionRepository supportSessionRepository;
    private final AiChatService aiChatService;

    @Override
    public SupportSessionResponse createSupportSession(SupportSessionRequest supportSessionRequest) {
        SupportSession supportSession = supportSessionRequest.toSupportSession();
        supportSession.setResult(aiChatService.respond(supportSession));
        return SupportSessionResponse.fromSupportSession(supportSessionRepository.save(supportSession));
    }

    @Override
    public PagedModel<SupportSessionResponse> getSupportSessions(Pageable pageable) {
        return new PagedModel<>(supportSessionRepository.findAll(pageable)
                .map(SupportSessionResponse::fromSupportSession));
    }

    @Override
    public SupportSessionResponse getSupportSession(Long id) {
        return SupportSessionResponse.fromSupportSession(supportSessionRepository.findById(id)
                .orElseThrow(SupportSessionNotFoundException::new));
    }
}
