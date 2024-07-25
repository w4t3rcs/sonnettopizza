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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Override
    @Caching(cacheable = @Cacheable("ingredientCache"))
    @Transactional
    public IngredientResponse createIngredient(IngredientRequest ingredientRequest) {
        return IngredientResponse.fromIngredient(ingredientRepository.save(ingredientRequest.toIngredient()));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<IngredientResponse> getIngredients(Pageable pageable) {
        return new PagedModel<>(ingredientRepository.findAll(pageable)
                .map(IngredientResponse::fromIngredient));
    }

    @Override
    @Cacheable("ingredientCache")
    @Transactional(readOnly = true)
    public IngredientResponse getIngredient(Long id) {
        return IngredientResponse.fromIngredient(ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFoundException::new));
    }

    @Override
    @Caching(put = @CachePut("ingredientCache"))
    @Transactional
    public IngredientResponse updateIngredient(Long id, IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFoundException::new);
        if (ingredientRequest.getName() != null) ingredient.setName(ingredientRequest.getName());
        if (ingredientRequest.getType() != null) ingredient.setType(ingredientRequest.getType());
        return IngredientResponse.fromIngredient(ingredientRepository.save(ingredient));
    }

    @Override
    @Caching(evict = @CacheEvict("ingredientCache"))
    @Transactional
    public Long deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
        return id;
    }
}
