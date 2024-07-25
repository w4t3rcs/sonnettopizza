package org.sonnetto.dish.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.dish.entity.Dish;
import org.sonnetto.dish.entity.Type;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DishRequest implements Serializable {
    private String name;
    private Type type;
    private List<Long> ingredientIds;

    @Valid
    public Dish toDish() {
        return new Dish(null, name, type, ingredientIds);
    }
}
