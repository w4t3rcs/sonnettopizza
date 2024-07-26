package org.sonnetto.dish.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.dish.service.IngredientChecker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class IngredientCheckerImpl implements IngredientChecker {
    @Override
    @CircuitBreaker(name = "ingredient-service", fallbackMethod = "fallback")
    public boolean checkExistence(Long id) {
        return WebClient.create("http://localhost:8082/api/v1.0/ingredients/" + id)
                .get()
                .retrieve()
                .toBodilessEntity()
                .block()
                .getStatusCode()
                .is2xxSuccessful();
    }

    private boolean fallback(Long id, Throwable throwable) {
        log.error("Oops, ingredient-service is currently unavailable! \n{}", throwable.getMessage());
        return false;
    }
}
