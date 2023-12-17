package com.samuel.shoppingms.factory;

import com.samuel.shoppingms.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Order> createListOrders() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var order = create();
            orders.add(order);
        }
        return orders;
    }
}
