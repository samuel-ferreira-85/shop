package com.samuel.workershop.service;

import com.samuel.workershop.model.Order;
import com.samuel.workershop.service.producer.OrderProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private OrderProducer orderProducer;

    public void noticarCliente(Order order) {
        log.info("Order recebido no notificar cliente: {}", order);
        var message = new SimpleMailMessage();
        message.setTo(order.getEmail());
        message.setSubject("Compra recebida");
        message.setText("Este é um e-mail de confirmação de compra recebida. " +
                "Agora vamos aprovar sua compra e brevemente você receberá um novo e-mail de confirmação." +
                "\nObrigado por comprar com a gente!!");
        javaMailSender.send(message);
        log.info("Customer notified successfully");
        orderProducer.sendOrder(order);
        log.info("Preparing order...");
    }

}
