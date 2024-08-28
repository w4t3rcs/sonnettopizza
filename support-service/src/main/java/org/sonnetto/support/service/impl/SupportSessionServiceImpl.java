package org.sonnetto.support.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.support.dto.SupportSessionRequest;
import org.sonnetto.support.dto.SupportSessionResponse;
import org.sonnetto.support.entity.SupportSession;
import org.sonnetto.support.exception.SupportSessionNotFoundException;
import org.sonnetto.support.producer.SupportSessionProducer;
import org.sonnetto.support.repository.SupportSessionRepository;
import org.sonnetto.support.service.AiChatService;
import org.sonnetto.support.service.SupportSessionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupportSessionServiceImpl implements SupportSessionService {
    private final SupportSessionRepository supportSessionRepository;
    private final SupportSessionProducer supportSessionProducer;
    private final AiChatService aiChatService;

    @Override
    @Caching(cacheable = @Cacheable("supportSessionCache"))
    @Transactional
    public SupportSessionResponse createSupportSession(SupportSessionRequest supportSessionRequest) {
        SupportSession supportSession = supportSessionRequest.toSupportSession();
        supportSession.setResult(aiChatService.respond(supportSession));
        SupportSession savedSupportSession = supportSessionRepository.save(supportSession);
        supportSessionProducer.sendSupportSession("support.created", savedSupportSession);
        return SupportSessionResponse.fromSupportSession(savedSupportSession);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<SupportSessionResponse> getSupportSessions(Pageable pageable) {
        return new PagedModel<>(supportSessionRepository.findAll(pageable)
                .map(SupportSessionResponse::fromSupportSession));
    }

    @Override
    @Cacheable("supportSessionCache")
    @Transactional(readOnly = true)
    public SupportSessionResponse getSupportSession(Long id) {
        return SupportSessionResponse.fromSupportSession(supportSessionRepository.findById(id)
                .orElseThrow(SupportSessionNotFoundException::new));
    }
}
