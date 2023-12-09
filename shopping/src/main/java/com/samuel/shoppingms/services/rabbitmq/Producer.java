package com.samuel.shoppingms.services.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.shoppingms.model.Order;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@AllArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper mapper;

    @PostMapping
    @SneakyThrows
    public void toSend(Order order) {
        String orderJson = mapper.writeValueAsString(order);
        rabbitTemplate.convertAndSend(queue.getName(), orderJson);
    }
}
