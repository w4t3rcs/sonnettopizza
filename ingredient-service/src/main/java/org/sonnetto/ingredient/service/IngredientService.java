package org.sonnetto.ingredient.service;

import org.sonnetto.ingredient.dto.IngredientRequest;
import org.sonnetto.ingredient.dto.IngredientResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface IngredientService {
    IngredientResponse createIngredient(IngredientRequest ingredientRequest);

    PagedModel<IngredientResponse> getIngredients(Pageable pageable);

    IngredientResponse getIngredient(Long id);

    IngredientResponse updateIngredient(Long id, IngredientRequest ingredientRequest);

    Long deleteIngredient(Long id);
}
