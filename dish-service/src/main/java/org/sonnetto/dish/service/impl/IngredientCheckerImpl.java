package org.sonnetto.dish.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.service.IngredientChecker;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class IngredientCheckerImpl implements IngredientChecker {
    @Override
    @CircuitBreaker(name = "ingredient-service", fallbackMethod = "fallback")
    public boolean checkExistence(DishRequest dishRequest) {
        Flux.fromIterable(dishRequest.getIngredientIds())
                .doOnNext(id -> WebClient.create("http://localhost:8082/api/v1.0/ingredients/" + id)
                        .get()
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, ClientResponse::createException))
                .collectList()
                .block();
        return true;
    }

    private String fallback(DishRequest dishRequest, RuntimeException exception) {
        return "Oops, it seems that ingredient-service is unavailable! \n" + exception.getMessage();
    }
}
