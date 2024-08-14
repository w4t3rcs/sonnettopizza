package org.sonnetto.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.Data;
import org.sonnetto.product.document.Price;
import org.sonnetto.product.document.Product;
import org.sonnetto.product.document.Type;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest implements Serializable {
    private String name;
    private Type type;
    private Price price;
    private List<Long> ingredientIds;

    @Valid
    public Product toProduct() {
        return new Product(this.getName(), this.getType(), this.getPrice(), this.getIngredientIds());
    }
}
