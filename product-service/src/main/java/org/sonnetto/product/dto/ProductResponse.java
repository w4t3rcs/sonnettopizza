package org.sonnetto.product.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sonnetto.product.document.Price;
import org.sonnetto.product.document.Product;
import org.sonnetto.product.document.Type;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private String name;
    private Type type;
    private Price price;
    private List<Long> ingredientIds;

    public static ProductResponse fromProduct(@Valid Product product) {
        return new ProductResponse(product.getName(), product.getType(), product.getPrice(), product.getIngredientIds());
    }
}
