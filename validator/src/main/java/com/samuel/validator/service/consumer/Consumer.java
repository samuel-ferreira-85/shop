package com.samuel.validator.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.validator.model.Order;
import com.samuel.validator.service.ValidatorService;
import com.samuel.validator.service.exceptions.InsufficientFundsException;
import com.samuel.validator.service.exceptions.LimitUnavailable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
@Component
public class Consumer {

    private final ObjectMapper mapper;
    private final ValidatorService validatorService;

    @RabbitListener(queues = "${queue.pending}")
    public void consumer(@Payload Message message) throws IOException {
        var order = mapper.readValue(message.getBody(), Order.class);
        log.info("Objeto consumido no VALIDATOR: {}", order);
        try {
            validatorService.validatorOrder(order);
        } catch (InsufficientFundsException | LimitUnavailable e) {
            e.printStackTrace();
        }
    }
}
