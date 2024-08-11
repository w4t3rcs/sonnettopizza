package org.sonnetto.order.client;

import org.sonnetto.order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "products", url = "${sonnetto.urls.product-service}")
public interface ProductClient {
    @RequestMapping(path = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> getProductHead(@PathVariable Long id);

    @GetMapping(path = "/{id}", params = "currency")
    ResponseEntity<ProductResponse> getProduct(@PathVariable Long id, @RequestParam String currency);
}
