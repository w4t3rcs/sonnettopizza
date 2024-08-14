package org.sonnetto.order.client;

import org.sonnetto.order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "products", url = "${sonnetto.urls.product-service}")
public interface ProductClient {
    @RequestMapping(path = "/{name}", method = RequestMethod.HEAD)
    ResponseEntity<?> getProductHead(@PathVariable String name);

    @GetMapping(path = "/{name}", params = "currency")
    ResponseEntity<ProductResponse> getProduct(@PathVariable String name, @RequestParam String currency);
}
