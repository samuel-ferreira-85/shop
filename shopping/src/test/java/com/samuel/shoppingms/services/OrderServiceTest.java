package com.samuel.shoppingms.services;

import com.samuel.shoppingms.factory.Factory;
import com.samuel.shoppingms.model.Order;
import com.samuel.shoppingms.repository.OrderRepository;
import com.samuel.shoppingms.services.rabbitmq.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private Producer producer;
    private Order order;

    @BeforeEach
    void setUp() throws Exception {
        order = Factory.create();
    }

    @DisplayName("Deve salvar um pedido com sucesso")
    @Test
    void saveShouldSaveOrderSuccessfully() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        doNothing().when(producer).toSend(any(Order.class));

        var orderSaved = orderService.save(order);

        assertNotNull(orderSaved);
        assertEquals(order.getCep(), orderSaved.getCep());
    }
}