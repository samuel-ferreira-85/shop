package com.samuel.shoppingms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.shoppingms.ShoppingApplication;
import com.samuel.shoppingms.factory.Factory;
import com.samuel.shoppingms.model.Order;
import com.samuel.shoppingms.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShoppingApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ObjectMapper mapper;
    private static final String ROUTE_ORDER = "/orders";
    private Order order;
    private Long id;

    @BeforeEach
    void setUp() {
        order = Factory.create();
        id = 1L;
    }

    @DisplayName("POST - Deve cadastrar um pedido no database")
    @Test
    void saveShouldSaveOrderSuccessfully() throws Exception {
        mockMvc.perform(post(ROUTE_ORDER)
                .content(mapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAll() {
    }

    @Test
    void getOne() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}