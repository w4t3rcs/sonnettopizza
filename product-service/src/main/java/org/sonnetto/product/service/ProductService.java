package org.sonnetto.product.service;

import org.sonnetto.product.dto.ProductRequest;
import org.sonnetto.product.dto.ProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    PagedModel<ProductResponse> getProducts(Pageable pageable);

    ProductResponse getProduct(String name);

    ProductResponse getProductWithConvertedPrice(String name, String currency);

    ProductResponse updateProduct(String name, ProductRequest productRequest);

    String deleteProduct(String name);
}
