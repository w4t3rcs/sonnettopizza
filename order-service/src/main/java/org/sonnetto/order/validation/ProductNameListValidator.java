package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sonnetto.order.client.ProductClient;
import org.sonnetto.order.config.ApplicationContextContainer;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class ProductNameListValidator implements ConstraintValidator<ProductNameList, List<String>> {
    private ProductClient productClient;

    @Override
    public void initialize(ProductNameList constraintAnnotation) {
        this.productClient = ApplicationContextContainer.getApplicationContext().getBean(ProductClient.class);
    }

    @Override
    public boolean isValid(List<String> names, ConstraintValidatorContext constraintValidatorContext) {
        if (names == null || names.isEmpty()) return false;
        return Boolean.TRUE.equals(Flux.fromIterable(names)
                .publishOn(Schedulers.boundedElastic())
                .map(name -> productClient.getProductHead(name).getStatusCode())
                .all(HttpStatusCode::is2xxSuccessful)
                .block());
    }
}
