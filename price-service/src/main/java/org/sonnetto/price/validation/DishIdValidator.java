package org.sonnetto.price.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class DishIdValidator implements ConstraintValidator<DishId, Long> {
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) return false;
        return Boolean.TRUE.equals(WebClient.create("http://localhost:8083/api/v1.0/dishes/" + id)
                .get()
                .retrieve()
                .toBodilessEntity()
                .block()
                .getStatusCode()
                .is2xxSuccessful());
    }
}
