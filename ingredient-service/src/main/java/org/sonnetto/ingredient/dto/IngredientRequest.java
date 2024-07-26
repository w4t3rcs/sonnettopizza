package org.sonnetto.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.ingredient.entity.Ingredient;
import org.sonnetto.ingredient.entity.Type;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientRequest implements Serializable {
    private String name;
    private Type type;

    @Valid
    public Ingredient toIngredient() {
        return new Ingredient(null, this.getName(), this.getType());
    }
}
