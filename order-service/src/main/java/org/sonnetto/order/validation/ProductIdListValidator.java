package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidatorContext;
import reactor.core.publisher.Flux;

import java.util.List;

public class ProductIdListValidator extends HttpResourceIdValidator<ProductIdList, List<Long>> {
    private static final String PRODUCT_URI = "http://product-service:8083/api/v1.0/products/{id}";

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        if (ids == null || ids.isEmpty()) return false;
        return Boolean.TRUE.equals(Flux.fromIterable(ids)
                .map(id -> this.isResourceValid(PRODUCT_URI, id))
                .all(Boolean::booleanValue)
                .block());
    }
}
