package com.samuel.workershop.service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.workershop.model.CreditCard;
import com.samuel.workershop.model.Order;
import com.samuel.workershop.service.CardService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
@Service
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper mapper;
    private final CardService cardService;

    @SneakyThrows
    @PostMapping
    public void sendOrder(Order order) {
        order.setCreditCard(CreditCard.builder()
                        .number(cardService.generateCard())
                        .limit(cardService.generateLimit())
                .build());
        var orderJson = mapper.writeValueAsString(order);
        rabbitTemplate.convertAndSend(queue.getName(), orderJson);
        log.info("Order assembled successfully in WorkerShopp - Order Producer: {}", orderJson);
    }
}
