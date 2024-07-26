package org.sonnetto.dish.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.dto.DishResponse;
import org.sonnetto.dish.entity.Dish;
import org.sonnetto.dish.exception.DishNotFoundException;
import org.sonnetto.dish.repository.DishRepository;
import org.sonnetto.dish.service.DishService;
import org.sonnetto.dish.service.IngredientChecker;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final IngredientChecker ingredientChecker;

    @Override
    @Caching(cacheable = @Cacheable("dishCache"))
    @Transactional
    public DishResponse createDish(DishRequest dishRequest) {
        if (dishRequest.getIngredientIds()
                .stream()
                .allMatch(ingredientChecker::checkExistence)) return DishResponse.fromDish(dishRepository.save(dishRequest.toDish()));
        else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
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
    public DishResponse updateDish(Long id, DishRequest dishRequest) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(DishNotFoundException::new);
        if (dishRequest.getName() != null) dish.setName(dish.getName());
        if (dishRequest.getType() != null) dish.setType(dish.getType());
        if (dishRequest.getIngredientIds() != null &&
                dishRequest.getIngredientIds()
                        .stream()
                        .allMatch(ingredientChecker::checkExistence)) dish.setIngredientIds(dish.getIngredientIds());
        return DishResponse.fromDish(dishRepository.save(dish));
    }

    @Override
    @Caching(evict = @CacheEvict("dishCache"))
    @Transactional
    public Long deleteDish(Long id) {
        dishRepository.deleteById(id);
        return id;
    }
}
