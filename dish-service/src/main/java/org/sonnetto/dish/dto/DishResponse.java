package org.sonnetto.dish.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.dish.entity.Dish;
import org.sonnetto.dish.entity.Type;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class DishResponse implements Serializable {
    private Long id;
    private String name;
    private Type type;
    private List<Long> ingredientIds;

    public static DishResponse fromDish(@Valid Dish dish) {
        return new DishResponse(dish.getId(), dish.getName(), dish.getType(), dish.getIngredientIds());
    }
}
