package org.sonnetto.product.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ingredients", url = "${sonnetto.urls.ingredient-service}")
public interface IngredientClient {
    @RequestMapping(path = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> getIngredientHead(@PathVariable Long id);
}
