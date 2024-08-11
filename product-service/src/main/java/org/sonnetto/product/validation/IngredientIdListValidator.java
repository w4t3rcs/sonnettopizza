package org.sonnetto.product.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sonnetto.product.client.IngredientClient;
import org.sonnetto.product.config.ApplicationContextContainer;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class IngredientIdListValidator implements ConstraintValidator<IngredientIdList, List<Long>> {
    private IngredientClient ingredientClient;

    @Override
    public void initialize(IngredientIdList constraintAnnotation) {
        this.ingredientClient = ApplicationContextContainer.getApplicationContext().getBean(IngredientClient.class);
    }

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        if (ids == null || ids.isEmpty()) return true;
        return Boolean.TRUE.equals(Flux.fromIterable(ids)
                .publishOn(Schedulers.boundedElastic())
                .map(id -> ingredientClient.getIngredientHead(id).getStatusCode())
                .all(HttpStatusCode::is2xxSuccessful)
                .block());
    }
}
