package org.sonnetto.order.controller;

import lombok.RequiredArgsConstructor;
import org.sonnetto.order.dto.OrderRequest;
import org.sonnetto.order.dto.OrderResponse;
import org.sonnetto.order.entity.Status;
import org.sonnetto.order.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> postOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public PagedModel<OrderResponse> getOrders(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    @GetMapping(params = "status")
    public PagedModel<OrderResponse> getOrdersByStatus(@RequestParam Status status,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return orderService.getOrdersByStatus(status, pageable);
    }

    @GetMapping(params = "city")
    public PagedModel<OrderResponse> getOrdersByCity(@RequestParam String city,
                                                       @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return orderService.getOrdersByCity(city, pageable);
    }

    @GetMapping(params = "street")
    public PagedModel<OrderResponse> getOrdersByStreet(@RequestParam String street,
                                                     @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return orderService.getOrdersByStreet(street, pageable);
    }

    @GetMapping(params = "postal_code")
    public PagedModel<OrderResponse> getOrdersByPostalCode(@RequestParam String postalCode,
                                                     @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return orderService.getOrdersByPostalCode(postalCode, pageable);
    }

    @GetMapping(params = "user_id")
    public PagedModel<OrderResponse> getOrdersByPostalCode(@RequestParam Long userId,
                                                           @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(size = 25) Pageable pageable) {
        return orderService.getOrdersByUserId(userId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }
}
