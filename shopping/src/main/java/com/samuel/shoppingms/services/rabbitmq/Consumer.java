package com.samuel.shoppingms.services.rabbitmq;

import com.samuel.shoppingms.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

public class Consumer {

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Order order) {
        System.out.println("Mensagem recebida: " + order);
    }
}
