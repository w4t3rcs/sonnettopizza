package org.sonnetto.dish.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.dto.DishResponse;
import org.sonnetto.dish.entity.Dish;
import org.sonnetto.dish.exception.DishNotFoundException;
import org.sonnetto.dish.exception.IngredientServiceUnavailableException;
import org.sonnetto.dish.repository.DishRepository;
import org.sonnetto.dish.service.DishService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    @Override
    @Caching(cacheable = @Cacheable("dishCache"))
    @Transactional
    @CircuitBreaker(name = "ingredient-service", fallbackMethod = "fallback")
    @TimeLimiter(name = "ingredient-service")
    @Retry(name = "ingredient-service")
    public CompletableFuture<DishResponse> createDish(DishRequest dishRequest) {
        return CompletableFuture.supplyAsync(() -> DishResponse.fromDish(dishRepository.save(dishRequest.toDish())));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<DishResponse> getDishes(Pageable pageable) {
        return new PagedModel<>(dishRepository.findAll(pageable)
                .map(DishResponse::fromDish));
    }

    @Override
    @Cacheable("dishCache")
    @Transactional(readOnly = true)
    public DishResponse getDish(Long id) {
        return DishResponse.fromDish(dishRepository.findById(id)
                .orElseThrow(DishNotFoundException::new));
    }

    @Override
    @Caching(put = @CachePut("dishCache"))
    @Transactional
    @CircuitBreaker(name = "ingredient-service", fallbackMethod = "fallback")
    @TimeLimiter(name = "ingredient-service")
    @Retry(name = "ingredient-service")
    public CompletableFuture<DishResponse> updateDish(Long id, DishRequest dishRequest) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(DishNotFoundException::new);
        if (dishRequest.getName() != null) dish.setName(dishRequest.getName());
        if (dishRequest.getType() != null) dish.setType(dishRequest.getType());
        if (dishRequest.getIngredientIds() != null) dish.setIngredientIds(dishRequest.getIngredientIds());

        return CompletableFuture.supplyAsync(() -> DishResponse.fromDish(dishRepository.save(dish)));
    }

    @Override
    @Caching(evict = @CacheEvict("dishCache"))
    @Transactional
    public Long deleteDish(Long id) {
        dishRepository.deleteById(id);
        return id;
    }

    private CompletableFuture<DishResponse> fallback(Throwable throwable) {
        log.error("{}\n{}" , throwable.getMessage(), Arrays.toString(throwable.getStackTrace()));
        throw new IngredientServiceUnavailableException(throwable);
    }
}
