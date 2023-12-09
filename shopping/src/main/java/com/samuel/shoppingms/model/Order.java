package com.samuel.shoppingms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @CPF
    private String cpf;
    @NotBlank
    private String cep;
    @NotNull
    @Positive
    private Long product;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOrder;

}
