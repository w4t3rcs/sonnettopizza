package org.sonnetto.price.repository;

import org.sonnetto.price.entity.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Page<Price> findAllByDishId(Long dishId, Pageable pageable);
}
