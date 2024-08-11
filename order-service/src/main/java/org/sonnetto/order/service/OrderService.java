package org.sonnetto.order.service;

import org.sonnetto.order.dto.OrderRequest;
import org.sonnetto.order.dto.OrderResponse;
import org.sonnetto.order.entity.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    PagedModel<OrderResponse> getOrders(Pageable pageable);

    PagedModel<OrderResponse> getOrdersByStatus(Status status, Pageable pageable);

    PagedModel<OrderResponse> getOrdersByCity(String city, Pageable pageable);

    PagedModel<OrderResponse> getOrdersByStreet(String street, Pageable pageable);

    PagedModel<OrderResponse> getOrdersByPostalCode(String postalCode, Pageable pageable);

    PagedModel<OrderResponse> getOrdersByUserId(Long userId, Pageable pageable);

    OrderResponse getOrder(Long id);

    OrderResponse updateOrder(Long id, OrderRequest orderRequest);

    OrderResponse updateOrderByStatus(Long id, Status status);

    Long deleteOrder(Long id);
}
