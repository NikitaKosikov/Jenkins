package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private OrderService orderService;

    private static Pageable pageable;
    private static Order order1;
    private static Order order2;
    private static User user;
    private static GiftCertificate giftCertificate;

    @BeforeEach
    public void initUseCase(){
        orderService = new OrderServiceImpl(orderRepository, userRepository, giftCertificateRepository);
    }

    @BeforeAll
    public static void setUp() {
        user = new User();
        user.setId(1L);
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        order1 = new Order(1L, new BigDecimal(100), user, giftCertificate,
                LocalDateTime.of(2020, 12, 12, 12, 0, 0));
        order2 = new Order(2L, new BigDecimal(100), user, giftCertificate,
                LocalDateTime.of(2020, 12, 12, 12, 0, 0));
        pageable = PageRequest.of(0, 2);
    }

    @Test
    void findOrderByIdPositiveResult() {
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order1));
        Optional<Order> order = orderService.findOrderById(1);

        assertTrue(order.isPresent());
        assertEquals(order.get().getId(), order1.getId());
    }

    @Test
    void findOrderByIdNegativeResult() {
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order2));

        Optional<Order> order = orderService.findOrderById(1);

        assertTrue(order.isPresent());
        assertNotEquals(order.get().getId(), order1.getId());
    }

    @Test
    void findOrderByIdNotFoundNegativeResult() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Order> actualOrder = orderService.findOrderById(1);

        assertFalse(actualOrder.isPresent());
    }

    @Test
    void makePurchasePositiveResult() {

        when(userRepository.findById(order1.getUser().getId())).thenReturn(Optional.ofNullable(user));
        when(giftCertificateRepository.findById(order1.getGiftCertificate().getId())).thenReturn(Optional.ofNullable(giftCertificate));
        Mockito.when(orderRepository.save(order1)).thenReturn(order1);

        Order actualOrder = orderService.makePurchase(order1);

        assertEquals(actualOrder, order1);
    }

    @Test
    void makePurchaseUserNotFoundNegativeResult() {

        when(userRepository.findById(order1.getUser().getId())).thenReturn(Optional.empty());
        when(giftCertificateRepository.findById(order1.getGiftCertificate().getId())).thenReturn(Optional.ofNullable(giftCertificate));

        Order actualOrder = orderService.makePurchase(order1);

        assertNull(actualOrder);
    }


    @Test
    void makePurchaseGiftCertificateNotFoundNegativeResult() {

        when(userRepository.findById(order1.getUser().getId())).thenReturn(Optional.ofNullable(user));
        when(giftCertificateRepository.findById(order1.getGiftCertificate().getId())).thenReturn(Optional.empty());

        Order actualOrder = orderService.makePurchase(order1);

        assertNull(actualOrder);
    }
}