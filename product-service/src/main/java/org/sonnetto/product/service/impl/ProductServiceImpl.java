package org.sonnetto.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.product.dto.ProductRequest;
import org.sonnetto.product.dto.ProductResponse;
import org.sonnetto.product.entity.Product;
import org.sonnetto.product.exception.ProductNotFoundException;
import org.sonnetto.product.repository.ProductRepository;
import org.sonnetto.product.service.PriceConversionService;
import org.sonnetto.product.service.ProductService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PriceConversionService priceConversionService;

    @Override
    @Caching(cacheable = @Cacheable("productCache"))
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        return ProductResponse.fromProduct(productRepository.save(productRequest.toProduct()));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ProductResponse> getProducts(Pageable pageable) {
        return new PagedModel<>(productRepository.findAll(pageable)
                .map(ProductResponse::fromProduct));
    }

    @Override
    @Cacheable("productCache")
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return ProductResponse.fromProduct(productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new));
    }

    @Override
    @Cacheable("convertedProductCache")
    @Transactional(readOnly = true)
    public ProductResponse getProductWithConvertedPrice(Long id, String currency) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        priceConversionService.convertPrice(product.getPrice(), currency);
        return ProductResponse.fromProduct(product);
    }

    @Override
    @Caching(put = @CachePut("productCache"))
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        if (productRequest.getName() != null) product.setName(productRequest.getName());
        if (productRequest.getType() != null) product.setType(productRequest.getType());
        if (productRequest.getIngredientIds() != null) product.setIngredientIds(productRequest.getIngredientIds());

        return ProductResponse.fromProduct(productRepository.save(product));
    }

    @Override
    @Caching(evict = @CacheEvict("productCache"))
    @Transactional
    public Long deleteProduct(Long id) {
        productRepository.deleteById(id);
        return id;
    }
}
