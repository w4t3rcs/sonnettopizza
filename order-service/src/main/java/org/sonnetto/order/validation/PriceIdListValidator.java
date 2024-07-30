package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidatorContext;
import reactor.core.publisher.Flux;

import java.util.List;

public class PriceIdListValidator extends HttpResourceIdValidator<PriceIdList, List<Long>> {
    private static final String PRICE_ID = "http://price-service:8084/api/v1.0/prices/{id}";

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        return Boolean.TRUE.equals(Flux.fromIterable(ids)
                .map(id -> this.isResourceValid(PRICE_ID, id))
                .all(Boolean::booleanValue)
                .block());
    }
}
