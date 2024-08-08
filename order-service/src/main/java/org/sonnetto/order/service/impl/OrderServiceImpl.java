package org.sonnetto.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.order.dto.OrderRequest;
import org.sonnetto.order.dto.OrderResponse;
import org.sonnetto.order.entity.Address;
import org.sonnetto.order.entity.Order;
import org.sonnetto.order.entity.Status;
import org.sonnetto.order.exception.OrderNotFoundException;
import org.sonnetto.order.repository.OrderRepository;
import org.sonnetto.order.service.OrderService;
import org.sonnetto.order.service.PaymentService;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    @Override
    @Caching(cacheable = @Cacheable("orderCache"))
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = orderRequest.toOrder();
        paymentService.processPayment(order);
        return OrderResponse.fromOrder(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<OrderResponse> getOrders(Pageable pageable) {
        return new PagedModel<>(orderRepository.findAll(pageable)
                .map(OrderResponse::fromOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<OrderResponse> getOrdersByStatus(Status status, Pageable pageable) {
        return new PagedModel<>(orderRepository.findAllByStatus(status, pageable)
                .map(OrderResponse::fromOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<OrderResponse> getOrdersByCity(String city, Pageable pageable) {
        return new PagedModel<>(orderRepository.findAllByAddressCity(city, pageable)
                .map(OrderResponse::fromOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<OrderResponse> getOrdersByStreet(String street, Pageable pageable) {
        return new PagedModel<>(orderRepository.findAllByAddressStreet(street, pageable)
                .map(OrderResponse::fromOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<OrderResponse> getOrdersByPostalCode(String postalCode, Pageable pageable) {
        return new PagedModel<>(orderRepository.findAllByAddressPostalCode(postalCode, pageable)
                .map(OrderResponse::fromOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<OrderResponse> getOrdersByUserId(Long userId, Pageable pageable) {
        return new PagedModel<>(orderRepository.findAllByUserId(userId, pageable)
                .map(OrderResponse::fromOrder));
    }

    @Override
    @Cacheable("orderCache")
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        return OrderResponse.fromOrder(orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new));
    }

    @Override
    @Caching(put = @CachePut("orderCache"))
    @Transactional
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        Address addressRequest = orderRequest.getAddress();
        if (addressRequest != null) {
            Address address = order.getAddress();
            if (addressRequest.getCountry() != null) address.setCountry(address.getCountry());
            if (addressRequest.getCity() != null) address.setCity(address.getCity());
            if (addressRequest.getStreet() != null) address.setStreet(address.getStreet());
            if (addressRequest.getHouseNumber() != null) address.setHouseNumber(address.getHouseNumber());
            if (addressRequest.getPostalCode() != null) address.setPostalCode(address.getPostalCode());
        }

        if (orderRequest.getStatus() != null) {
            order.setStatus(orderRequest.getStatus());
        }

        if (orderRequest.getUserId() != null) {
            order.setUserId(order.getUserId());
        }

        return OrderResponse.fromOrder(orderRepository.save(order));
    }

    @Override
    @Caching(evict = @CacheEvict("orderCache"))
    @Transactional
    public Long deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return id;
    }
}