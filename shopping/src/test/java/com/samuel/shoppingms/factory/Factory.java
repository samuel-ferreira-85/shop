package com.samuel.shoppingms.factory;

import com.samuel.shoppingms.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Factory {
    public static Order create() {
        return Order.builder()
                .name("Samuel Ferreira")
                .cpf("75399898071")
                .email("samuel@email.com")
                .product(1L)
                .price(BigDecimal.valueOf(999.9))
                .dateOrder(LocalDate.of(2023, 12, 30))
                .cep("62840000")
                .build();
    }

}
