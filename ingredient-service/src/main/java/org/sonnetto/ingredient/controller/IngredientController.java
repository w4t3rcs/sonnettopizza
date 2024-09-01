package org.sonnetto.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.sonnetto.ingredient.dto.IngredientRequest;
import org.sonnetto.ingredient.dto.IngredientResponse;
import org.sonnetto.ingredient.service.IngredientService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<IngredientResponse> postIngredient(@RequestBody IngredientRequest ingredientRequest) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredientRequest));
    }

    @GetMapping
    public PagedModel<IngredientResponse> getIngredients(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return ingredientService.getIngredients(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getIngredient(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getIngredient(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> updateIngredient(@PathVariable Long id, @RequestBody IngredientRequest ingredientRequest) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id, ingredientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteIngredient(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.deleteIngredient(id));
    }
}