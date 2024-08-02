package org.sonnetto.order.repository;

import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByStatus(Status status, Pageable pageable);

    Page<Order> findAllByAddressCity(String city, Pageable pageable);

    Page<Order> findAllByAddressStreet(String street, Pageable pageable);

    Page<Order> findAllByAddressPostalCode(String postalCode, Pageable pageable);

    Page<Order> findAllByUserId(Long userId, Pageable pageable);
}