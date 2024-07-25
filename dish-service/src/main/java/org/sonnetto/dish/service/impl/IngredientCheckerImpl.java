package org.sonnetto.dish.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.exception.IngredientServiceUnavailableException;
import org.sonnetto.dish.service.IngredientChecker;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class IngredientCheckerImpl implements IngredientChecker {
    @Override
    @CircuitBreaker(name = "ingredient-service", fallbackMethod = "fallback")
    public boolean checkExistence(DishRequest dishRequest) {
        AtomicBoolean exists = new AtomicBoolean(true);
        Flux.fromIterable(dishRequest.getIngredientIds())
                .doOnNext(id -> WebClient.create("http://localhost:8082/api/v1.0/ingredients/" + id)
                        .get()
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse -> {
                            exists.set(false);
                            return clientResponse.createException();
                        }))
                .collectList()
                .block();
        return exists.get();
    }

    private boolean fallback(DishRequest dishRequest, Throwable throwable) {
        throw new IngredientServiceUnavailableException(dishRequest, throwable);
    }
}
