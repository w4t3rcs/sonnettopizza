package org.sonnetto.ingredient.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.ingredient.dto.IngredientRequest;
import org.sonnetto.ingredient.dto.IngredientResponse;
import org.sonnetto.ingredient.entity.Ingredient;
import org.sonnetto.ingredient.exception.IngredientNotFoundException;
import org.sonnetto.ingredient.repository.IngredientRepository;
import org.sonnetto.ingredient.service.IngredientService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Override
    @Caching(cacheable = @Cacheable("ingredientCache"))
    public IngredientResponse createIngredient(IngredientRequest ingredientRequest) {
        return IngredientResponse.fromIngredient(ingredientRepository.save(ingredientRequest.toIngredient()));
    }

    @Override
    public PagedModel<IngredientResponse> getIngredients(Pageable pageable) {
        return new PagedModel<>(ingredientRepository.findAll(pageable)
                .map(IngredientResponse::fromIngredient));
    }

    @Override
    @Cacheable("ingredientCache")
    public IngredientResponse getIngredient(Long id) {
        return IngredientResponse.fromIngredient(ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFoundException::new));
    }

    @Override
    @Caching(put = @CachePut("ingredientCache"))
    public IngredientResponse updateIngredient(Long id, IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFoundException::new);
        if (ingredientRequest.getName() != null) ingredient.setName(ingredientRequest.getName());
        if (ingredientRequest.getType() != null) ingredient.setType(ingredientRequest.getType());
        return IngredientResponse.fromIngredient(ingredientRepository.save(ingredient));
    }

    @Override
    @Caching(evict = @CacheEvict("ingredientCache"))
    public Long deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
        return id;
    }
}
