package org.sonnetto.notification.controller;

import lombok.RequiredArgsConstructor;
import org.sonnetto.notification.dto.NotificationResponse;
import org.sonnetto.notification.service.NotificationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1.0/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public PagedModel<NotificationResponse> getNotifications(@SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return notificationService.getNotifications(pageable);
    }

    @GetMapping(params = "target")
    public PagedModel<NotificationResponse> getNotificationsByTarget(@RequestParam String target,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return notificationService.getNotificationsByTarget(target, pageable);
    }

    @GetMapping(params = "body")
    public PagedModel<NotificationResponse> getNotificationsByBody(@RequestParam String body,
                                                                     @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return notificationService.getNotificationsByBody(body, pageable);
    }

    @GetMapping(params = "sent_date")
    public PagedModel<NotificationResponse> getNotificationsBySentDate(@RequestParam LocalDateTime sentDate,
                                                                   @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return notificationService.getNotificationsBySentDate(sentDate, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotification(id));
    }
}
