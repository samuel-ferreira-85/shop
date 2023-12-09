package com.samuel.workershop.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.workershop.model.Order;
import com.samuel.workershop.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class Consumer {

    private final ObjectMapper mapper;
    private final EmailService emailService;

    public Consumer(ObjectMapper mapper, EmailService emailService) {
        this.mapper = mapper;
        this.emailService = emailService;
    }

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) throws IOException {
        Order order = mapper.readValue(message.getBody(), Order.class);
        log.info("purchase order received: {}", order);
        emailService.noticarCliente(order);
    }
}
