package org.sonnetto.support.controller;

import lombok.RequiredArgsConstructor;
import org.sonnetto.support.dto.SupportSessionRequest;
import org.sonnetto.support.dto.SupportSessionResponse;
import org.sonnetto.support.service.SupportSessionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/support")
@RequiredArgsConstructor
public class SupportController {
    private final SupportSessionService supportSessionService;

    @PostMapping
    public ResponseEntity<SupportSessionResponse> postSupportSession(@RequestBody SupportSessionRequest supportSessionRequest) {
        return ResponseEntity.ok(supportSessionService.createSupportSession(supportSessionRequest));
    }

    @GetMapping
    public PagedModel<SupportSessionResponse> getSupportSessions(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return supportSessionService.getSupportSessions(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportSessionResponse> getSupportSession(@PathVariable Long id) {
        return ResponseEntity.ok(supportSessionService.getSupportSession(id));
    }
}
