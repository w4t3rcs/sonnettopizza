package org.sonnetto.product.document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.sonnetto.product.validation.IngredientIdList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Document(collection = "products")
public class Product implements Serializable {
    @Id
    @NotBlank
    @Length(max = 64)
    private String name;
    @NotNull
    private Type type;
    @Valid
    private Price price;
    @IngredientIdList
    private List<Long> ingredientIds;
}
