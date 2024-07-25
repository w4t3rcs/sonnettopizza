package org.sonnetto.dish.service;

import org.sonnetto.dish.dto.DishRequest;
import org.sonnetto.dish.dto.DishResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface DishService {
    DishResponse createDish(DishRequest dishRequest);

    PagedModel<DishResponse> getDishes(Pageable pageable);

    DishResponse getDish(Long id);

    DishResponse updateDish(Long id, DishRequest dishRequest);

    Long deleteDish(Long id);
}
