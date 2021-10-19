package com.epam.esm.service;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {
    /**
     *  Looking for all orders
     *
     * @param pageable the contains information about the data selection
     * @return the found list of orders
     */
    Page<Order> findAllOrders(Pageable pageable);

    /**
     * Looking for order by id
     *
     * @param id the id of order
     * @return found order
     */
    Optional<Order> findOrderById(long id);

    /**
     * Make a purchase of gift certificate by user
     *
     * @param order the order that will be created
     * @return created order
     */
    Order makePurchase(Order order);
}
