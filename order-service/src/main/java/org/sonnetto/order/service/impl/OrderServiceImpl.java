package org.sonnetto.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.order.dto.OrderRequest;
import org.sonnetto.order.dto.OrderResponse;
import org.sonnetto.order.entity.Status;
import org.sonnetto.order.repository.OrderRepository;
import org.sonnetto.order.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public PagedModel<OrderResponse> getOrders(Pageable pageable) {
        return null;
    }

    @Override
    public PagedModel<OrderResponse> getOrdersByStatus(Status status, Pageable pageable) {
        return null;
    }

    @Override
    public PagedModel<OrderResponse> getOrdersByAddressStreet(String street, Pageable pageable) {
        return null;
    }

    @Override
    public PagedModel<OrderResponse> getOrdersByAddressPostalCode(String postalCode, Pageable pageable) {
        return null;
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        return null;
    }

    @Override
    public Long deleteOrder(Long id) {
        return 0L;
    }
}
