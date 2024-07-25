package org.sonnetto.dish.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.dto.DishResponse;
import org.sonnetto.dish.entity.Dish;
import org.sonnetto.dish.exception.DishNotFoundException;
import org.sonnetto.dish.repository.DishRepository;
import org.sonnetto.dish.service.DishService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    @Override
    @Caching(cacheable = @Cacheable("dishCache"))
    public DishResponse createDish(DishRequest dishRequest) {
        return DishResponse.fromDish(dishRepository.save(dishRequest.toDish()));
    }

    @Override
    public PagedModel<DishResponse> getDishes(Pageable pageable) {
        return new PagedModel<>(dishRepository.findAll(pageable)
                .map(DishResponse::fromDish));
    }

    @Override
    @Cacheable("dishCache")
    public DishResponse getDish(Long id) {
        return DishResponse.fromDish(dishRepository.findById(id)
                .orElseThrow(DishNotFoundException::new));
    }

    @Override
    @Caching(put = @CachePut("dishCache"))
    public DishResponse updateDish(Long id, DishRequest dishRequest) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(DishNotFoundException::new);
        if (dishRequest.getName() != null) dish.setName(dish.getName());
        if (dishRequest.getType() != null) dish.setType(dish.getType());
        if (dishRequest.getIngredientIds() != null) dish.setIngredientIds(dish.getIngredientIds());
        return DishResponse.fromDish(dishRepository.save(dish));
    }

    @Override
    @Caching(evict = @CacheEvict("dishCache"))
    public Long deleteDish(Long id) {
        dishRepository.deleteById(id);
        return id;
    }
}
