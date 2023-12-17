package com.samuel.shoppingms.controller;

import com.samuel.shoppingms.model.Order;
import com.samuel.shoppingms.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody @Valid Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAll(Pageable pageable) {
        var allOrders = orderService.getAll(pageable);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        var order = orderService.getOne(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id,
                                        @RequestBody Order order) {
        Order orderUpdated = orderService.update(id, order);
        return ResponseEntity.ok(orderUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
