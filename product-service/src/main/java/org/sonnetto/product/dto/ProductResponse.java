package org.sonnetto.product.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.product.entity.Price;
import org.sonnetto.product.entity.Product;
import org.sonnetto.product.entity.Type;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private Type type;
    private Price price;
    private List<Long> ingredientIds;

    public static ProductResponse fromProduct(@Valid Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getType(), product.getPrice(), product.getIngredientIds());
    }
}
