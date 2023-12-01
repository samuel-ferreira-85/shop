package com.samuel.shoppingms.services.rabbitmq;

import com.samuel.shoppingms.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@AllArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @PostMapping
    public void toSend(Order order) {
        rabbitTemplate.convertAndSend(queue.getName(), order);
    }
}
