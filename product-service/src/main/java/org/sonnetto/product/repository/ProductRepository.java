package org.sonnetto.product.repository;

import org.sonnetto.product.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
