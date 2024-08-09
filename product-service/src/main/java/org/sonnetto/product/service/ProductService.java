package org.sonnetto.product.service;

import org.sonnetto.product.dto.ProductRequest;
import org.sonnetto.product.dto.ProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    PagedModel<ProductResponse> getProducts(Pageable pageable);

    ProductResponse getProduct(Long id);

    ProductResponse getProductWithConvertedPrice(Long id, String currency);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    Long deleteProduct(Long id);
}
