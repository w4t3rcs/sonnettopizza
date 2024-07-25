package org.sonnetto.dish.service;

import org.sonnetto.dish.dto.DishRequest;

public interface IngredientChecker {
    boolean checkExistence(DishRequest dishRequest);
}
