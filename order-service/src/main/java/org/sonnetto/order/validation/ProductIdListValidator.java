package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sonnetto.order.client.ProductClient;
import org.sonnetto.order.config.ApplicationContextContainer;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class ProductIdListValidator implements ConstraintValidator<ProductIdList, List<Long>> {
    private ProductClient productClient;

    @Override
    public void initialize(ProductIdList constraintAnnotation) {
        this.productClient = ApplicationContextContainer.getApplicationContext().getBean(ProductClient.class);
    }

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        if (ids == null || ids.isEmpty()) return false;
        return Boolean.TRUE.equals(Flux.fromIterable(ids)
                .publishOn(Schedulers.boundedElastic())
                .map(id -> productClient.getProductHead(id).getStatusCode())
                .all(HttpStatusCode::is2xxSuccessful)
                .block());
    }
}
