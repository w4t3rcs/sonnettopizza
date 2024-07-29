package org.sonnetto.price.controller;

import lombok.RequiredArgsConstructor;
import org.sonnetto.price.dto.PriceRequest;
import org.sonnetto.price.dto.PriceResponse;
import org.sonnetto.price.service.PriceService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1.0/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @PostMapping
    public CompletableFuture<PriceResponse> postPrice(@RequestBody PriceRequest priceRequest) {
        return priceService.createPrice(priceRequest);
    }

    @GetMapping
    public PagedModel<PriceResponse> getPrices(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return priceService.getPrices(pageable);
    }

    @GetMapping(params = "convert_to")
    public PagedModel<PriceResponse> getConvertedPrices(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable,
            @RequestParam(name = "convert_to") String code) {
        return priceService.getConvertedPrices(pageable, code);
    }

    @GetMapping(params = "dish_id")
    public PagedModel<PriceResponse> getPricesByDishId(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable,
            @RequestParam(name = "dish_id") Long dishId) {
        return priceService.getPricesByDishId(dishId, pageable);
    }

    @GetMapping(params = {"convert_to", "dish_id"})
    public PagedModel<PriceResponse> getConvertedPricesByDishId(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable,
            @RequestParam(name = "dish_id") Long dishId,
            @RequestParam(name = "convert_to") String code) {
        return priceService.getConvertedPricesByDishId(dishId, pageable, code);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceResponse> getPrice(@PathVariable Long id) {
        return ResponseEntity.ok(priceService.getPrice(id));
    }

    @GetMapping(path = "/{id}", params = "convert_to")
    public ResponseEntity<PriceResponse> getPrice(@PathVariable Long id,
                                                  @RequestParam(name = "convert_to") String code) {
        return ResponseEntity.ok(priceService.getConvertedPrice(id, code));
    }

    @PutMapping("/{id}")
    public CompletableFuture<PriceResponse> updatePrice(@PathVariable Long id, @RequestBody PriceRequest priceRequest) {
        return priceService.updatePrice(id, priceRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePrice(@PathVariable Long id) {
        return ResponseEntity.ok(priceService.deletePrice(id));
    }
}
