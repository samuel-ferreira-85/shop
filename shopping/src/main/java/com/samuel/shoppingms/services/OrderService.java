package com.samuel.shoppingms.services;

import com.samuel.shoppingms.model.Order;
import com.samuel.shoppingms.repository.OrderRepository;
import com.samuel.shoppingms.services.exceptions.EntidadeNaoEncontradaException;
import com.samuel.shoppingms.services.rabbitmq.Producer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private Producer producer;

    public Order save(Order order) {
        var orderSaved = orderRepository.save(order);
        log.info("Order saved: {}", orderSaved);
        producer.toSend(orderSaved);
        return orderSaved;
    }

    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getOne(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entity not found for ID: " + id));
    }

    public Order update(Long id, Order order) {
        var orderSaved = getOne(id);
        BeanUtils.copyProperties(order, orderSaved, "id");
        return orderSaved;
    }

    public void delete(Long id) {
        var orderSaved = getOne(id);
        orderRepository.delete(orderSaved);
    }
}
