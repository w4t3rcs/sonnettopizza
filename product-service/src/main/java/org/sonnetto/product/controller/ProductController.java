package org.sonnetto.product.controller;

import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/v1.0/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> postProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @GetMapping
    public PagedModel<ProductResponse> getProducts(
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProduct(name));
    }

    @GetMapping(path = "/{name}", params = "currency")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String name, @RequestParam String currency) {
        return ResponseEntity.ok(productService.getProductWithConvertedPrice(name, currency));
    }

    @PutMapping("/{name}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String name, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(name, productRequest));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.deleteProduct(name));
    }
}
