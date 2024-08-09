package org.sonnetto.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.sonnetto.product.dto.ProductRequest;
import org.sonnetto.product.dto.ProductResponse;
import org.sonnetto.product.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1.0/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @SneakyThrows
    @PostMapping
    public CompletableFuture<ProductResponse> postProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    public PagedModel<ProductResponse> getProducts(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping(path = "/{id}", params = "currency")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id, @RequestParam String currency) {
        return ResponseEntity.ok(productService.getProductWithConvertedPrice(id, currency));
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public CompletableFuture<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
