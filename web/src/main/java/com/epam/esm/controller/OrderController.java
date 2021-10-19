package com.epam.esm.controller;

import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class OrderController {

    private final LinkAdding<Order> linkAddingOrder;

    private final OrderService orderService;

    @Autowired
    public OrderController(LinkAdding<Order> linkAddingOrder, OrderService orderService) {
        this.linkAddingOrder = linkAddingOrder;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Page<Order>> findAllOrders(Pageable pageable){
        Page<Order> orders = orderService.findAllOrders(pageable);
        orders.forEach(linkAddingOrder::addLink);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> findOrderById(@PathVariable long id){
        Optional<Order> order = orderService.findOrderById(id);
        order.ifPresent(linkAddingOrder::addLink);
        return  new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> makePurchase(@RequestBody Order order){
        order = orderService.makePurchase(order);
        linkAddingOrder.addLink(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
