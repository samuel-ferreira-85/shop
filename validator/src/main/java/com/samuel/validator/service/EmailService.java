package com.samuel.validator.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.samuel.validator.model.Order;

@Slf4j
@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void notifyCustomerSuccessfulPurchase(Order order) {
        var message = new SimpleMailMessage();
        message.setTo(order.getEmail());
        message.setSubject("Compra confirmada");
        message.setText("Olá " + order.getName() + "!"
                + "Sua compra foi aprovada!\n" +
                "Em breve vc receberá seu código de rastreio!\n" +
                "Obrigado por comprar com a gente");
        javaMailSender.send(message);
        log.info("Customer notified successfully");
    }

    public void notifyCustomerInsufficientFunds(Order order) {
        var message = new SimpleMailMessage();
        message.setTo(order.getEmail());
        message.setSubject("Compra não processada");
        message.setText("Olá " + order.getName() + "!"
                + "Sua compra não foi finalizada.\n" +
                "Seu saldo é insuficiente.\n" +
                "Tente com outro cartão de crédito.");
        javaMailSender.send(message);
        log.info("Customer notified successfully");
    }

    public void notifyLimitUnavailable(Order order) {
        var message = new SimpleMailMessage();
        message.setTo(order.getEmail());
        message.setSubject("Compra não processada");
        message.setText("Olá " + order.getName() + "!"
                + "Sua compra não foi finalizada.\n" +
                "Limite nsuficiente para efetuar a compra.\n" +
                "Tente com outro cartão de crédito.");
        javaMailSender.send(message);
        log.info("Customer notified successfully");
    }
}
