package org.sonnetto.support.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "users", url = "${sonnetto.urls.user-service}")
public interface UserClient {
    @RequestMapping(path = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> getUserHead(@PathVariable Long id);
}
