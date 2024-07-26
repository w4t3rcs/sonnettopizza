package org.sonnetto.dish.controller;

import lombok.RequiredArgsConstructor;
import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.dto.DishResponse;
import org.sonnetto.dish.service.DishService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @PostMapping
    public ResponseEntity<DishResponse> postDish(@RequestBody DishRequest dishRequest) {
        return new ResponseEntity<>(dishService.createDish(dishRequest), HttpStatus.CREATED);
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

    @PutMapping("/{id}")
    public ResponseEntity<DishResponse> updateDish(@PathVariable Long id, @RequestBody DishRequest dishRequest) {
        return ResponseEntity.ok(dishService.updateDish(id, dishRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDish(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.deleteDish(id));
    }
}
