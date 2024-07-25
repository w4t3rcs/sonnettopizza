package org.sonnetto.dish.exception;

import org.sonnetto.dish.dto.DishRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class IngredientServiceUnavailableException extends RuntimeException {
    public IngredientServiceUnavailableException(DishRequest dishRequest, Throwable cause) {
        super(dishRequest.getName(), cause);
    }
}
