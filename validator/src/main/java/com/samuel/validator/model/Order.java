package com.samuel.validator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private String name;
    private String email;
    private Long product;
    private BigDecimal price;
    private LocalDate dateOrder;
    private String cpf;
    private String cep;

    private CreditCard creditCard;
}
