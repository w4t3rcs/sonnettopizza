package org.sonnetto.product.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

public class IngredientIdListValidator implements ConstraintValidator<IngredientIdList, List<Long>> {
    private static final String INGREDIENT_URI = "http://ingredient-service:8082/api/v1.0/ingredients/{id}";
    private WebClient webClient;

    @Override
    public void initialize(IngredientIdList constraintAnnotation) {
        this.webClient = WebClient.create();
    }

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        if (ids == null || ids.isEmpty()) return true;
        return Boolean.TRUE.equals(Flux.fromIterable(ids)
                .flatMap(id -> webClient.head()
                        .uri(INGREDIENT_URI, id)
                        .retrieve()
                        .toBodilessEntity()
                        .map(response -> response.getStatusCode().is2xxSuccessful())
                        .onErrorReturn(false))
                .all(Boolean::booleanValue)
                .block());
    }
}
