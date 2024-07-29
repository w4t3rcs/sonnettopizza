package org.sonnetto.dish.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.dto.DishResponse;
import org.sonnetto.dish.service.DishService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1.0/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @SneakyThrows
    @PostMapping
    public CompletableFuture<DishResponse> postDish(@RequestBody DishRequest dishRequest) {
        return dishService.createDish(dishRequest);
    }

    @GetMapping
    public PagedModel<DishResponse> getDishes(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return dishService.getDishes(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getDish(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getDish(id));
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public CompletableFuture<DishResponse> updateDish(@PathVariable Long id, @RequestBody DishRequest dishRequest) {
        return dishService.updateDish(id, dishRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDish(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.deleteDish(id));
    }
}
