package com.samuel.workershop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order implements Serializable {
    private static final long serialVersionUID = 1;

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
