package com.samuel.shoppingms.services;

import com.samuel.shoppingms.model.Order;
import com.samuel.shoppingms.repository.OrderRepository;
import com.samuel.shoppingms.services.rabbitmq.Producer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
}
