package org.sonnetto.ingredient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientNotFoundException extends RuntimeException {
}