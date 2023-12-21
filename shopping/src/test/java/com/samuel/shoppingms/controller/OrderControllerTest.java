package com.samuel.shoppingms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.shoppingms.ShoppingApplication;
import com.samuel.shoppingms.factory.Factory;
import com.samuel.shoppingms.model.Order;
import com.samuel.shoppingms.services.OrderService;
import com.samuel.shoppingms.services.rabbitmq.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShoppingApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderService orderService;
    @MockBean
    private Producer producer;
    @Autowired
    private ObjectMapper mapper;
    private static final String ROUTE_ORDER = "/orders";
    private static final String ROUTE_ORDER_ID = "/orders/{id}";
    private Order order;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        order = Factory.create();
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @DisplayName("POST - Deve cadastrar um pedido no database")
    @Test
    void saveShouldReturnOKSaveOrderSuccessfully() throws Exception {
        doNothing().when(producer).toSend(any(Order.class));
        mockMvc.perform(post(ROUTE_ORDER)
                .content(mapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L));
    }

    @DisplayName("GET - Deve retornar uma lista paginada de pedidos")
    @Test
    void getAllShouldReturnOKAndListOrderPage() throws Exception {
        mockMvc.perform(get(ROUTE_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("GET - Deve retornar um pedido quando o ID existe")
    @Test
    void getOneShouldReturnOKAndOrderWhenIdExisting() throws Exception {
        mockMvc.perform(get(ROUTE_ORDER_ID, existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Samuel Ferreira"));

    }

    @DisplayName("GET - Deve retornar NOT_FOUND quando o ID não existe")
    @Test
    void getOneShouldReturnNotFoundWhenNonExistingId() throws Exception {
        mockMvc.perform(get(ROUTE_ORDER_ID, nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("PUT - Deve retornar OK quando o ID existe")
    @Test
    void updateShouldReturnOkAndOrderUpdatedWhenIdExisting() throws Exception {
        mockMvc.perform(put(ROUTE_ORDER_ID, existingId)
                .content(mapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("PUT - Deve retornar NOT_FOUND quando o ID não existe")
    @Test
    void updateShouldReturnNotFoundWhenNonExistingId() throws Exception {
        mockMvc.perform(put(ROUTE_ORDER_ID, nonExistingId)
                        .content(mapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("DELETE - Deve retornar NO_CONTENT quando o ID existe")
    @Test
    void deleteShouldReturnNoContentAndDeleteOrderWhenNonExistingId() throws Exception {
        mockMvc.perform(delete(ROUTE_ORDER_ID, existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("DELETE - Deve retornar NOT_FOUND quando o ID não existe")
    @Test
    void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception {
        mockMvc.perform(put(ROUTE_ORDER_ID, nonExistingId)
                        .content(mapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}