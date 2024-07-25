package org.sonnetto.dish.repository;

import org.sonnetto.dish.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
