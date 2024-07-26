package org.sonnetto.dish.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class IngredientIdListValidator implements ConstraintValidator<IngredientIdList, List<Long>> {
    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        if (ids == null || ids.isEmpty()) return true;
        return Boolean.TRUE.equals(Flux.fromIterable(ids)
                .publishOn(Schedulers.boundedElastic())
                .map(id -> WebClient.create("http://localhost:8082/api/v1.0/ingredients/" + id)
                        .get()
                        .retrieve()
                        .toBodilessEntity()
                        .block()
                        .getStatusCode()
                        .is2xxSuccessful())
                .all(aBoolean -> aBoolean)
                .block());
    }
}
