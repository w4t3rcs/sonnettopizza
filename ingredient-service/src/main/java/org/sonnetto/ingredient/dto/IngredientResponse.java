package org.sonnetto.ingredient.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.ingredient.entity.Ingredient;
import org.sonnetto.ingredient.entity.Type;

@Data
@AllArgsConstructor
public class IngredientResponse {
    private Long id;
    private String name;
    private Type type;

    public static IngredientResponse fromIngredient(@Valid Ingredient ingredient) {
        return new IngredientResponse(ingredient.getId(), ingredient.getName(), ingredient.getType());
    }
}
