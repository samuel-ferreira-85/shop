package com.samuel.shoppingms.services;

import com.samuel.shoppingms.factory.Factory;
import com.samuel.shoppingms.model.Order;
import com.samuel.shoppingms.repository.OrderRepository;
import com.samuel.shoppingms.services.exceptions.EntidadeNaoEncontradaException;
import com.samuel.shoppingms.services.rabbitmq.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
    private PageImpl<Order> orderPage;
    private Long idExisting;
    private Long nonExistingID;

    @BeforeEach
    void setUp() throws Exception {
        order = Factory.create();
        orderPage = new PageImpl<>(List.of(order));
        idExisting = 1L;
        nonExistingID = 2L;
    }

    @DisplayName("SAVE         - Deve salvar um pedido com sucesso")
    @Test
    void saveShouldSaveOrderSuccessfully() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        doNothing().when(producer).toSend(any(Order.class));

        var orderSaved = orderService.save(order);

        assertNotNull(orderSaved);
        assertEquals(order.getCep(), orderSaved.getCep());
    }

    @DisplayName("GET_ALL    - Deve buscar todos os pedidos")
    @Test
    void getAllShouldReturnListOrders() {
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orderPage);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Order> page = orderService.getAll(pageable);

        assertNotNull(page);
        verify(orderRepository).findAll(pageable);
    }

    @DisplayName("GET_ONE   - Deve retornar um pedido quando ID existe")
    @Test
    void getOneShouldReturnOrderWhenIdExisting() {
        when(orderRepository.findById(idExisting)).thenReturn(Optional.of(order));

        var orderExisting = orderService.getOne(idExisting);

        assertNotNull(orderExisting);
        verify(orderRepository).findById(idExisting);
    }

    @DisplayName("GET_ONE   - Deve lançar EntidadeNaoEncontradaException quando o ID não existir")
    @Test
    void getOneShouldThrowEntidadeNaoEncontradaExceptionWhenNonExistingId() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> orderService.getOne(nonExistingID));
        verify(orderRepository).findById(nonExistingID);
    }

    @DisplayName("GET_ONE   - Deve atualizar um pedido quando ID existe")
    @Test
    void updateShouldReturnOrderWhenIdExisting() {
        when(orderRepository.findById(idExisting)).thenReturn(Optional.of(order));
        var orderUpdated = orderService.update(idExisting, order);

        assertNotNull(orderUpdated);
        verify(orderRepository).findById(idExisting);
    }

    @DisplayName("UPDATE     - Deve lançar EntidadeNaoEncontradaException quando o ID não existir")
    @Test
    void updateShouldThrowEntidadeNaoEncontradaExceptionWhenNonExistingId() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> orderService.update(nonExistingID, order));
        verify(orderRepository).findById(nonExistingID);
    }

    @DisplayName("DELETE     - Deve deletar um pedido quando ID existir")
    @Test
    void deleteShouldDeleteOrderWhenIdExisting() {
        when(orderRepository.findById(idExisting)).thenReturn(Optional.of(order));
        assertDoesNotThrow(() -> orderService.delete(idExisting));
        verify(orderRepository, times(1)).delete(order);
    }

    @DisplayName("DELETE     - Deve lançar EntidadeNaoEncontradaException quando o ID não existir")
    @Test
    void deleteShouldThrowEntidadeNaoEncontradaExceptionWhenNonExistingId() {
        doThrow(EntidadeNaoEncontradaException.class).when(orderRepository).findById(nonExistingID);

        assertThrows(EntidadeNaoEncontradaException.class, () -> orderService.delete(nonExistingID));
        verify(orderRepository, never()).delete(order);
    }
}