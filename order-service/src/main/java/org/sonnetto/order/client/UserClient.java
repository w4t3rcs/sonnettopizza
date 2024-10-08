package org.sonnetto.order.client;

import org.sonnetto.order.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users", url = "${sonnetto.urls.user-service}")
public interface UserClient {
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable Long id);
}
