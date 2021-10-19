package com.epam.esm.entity.audit;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class OrderAudit {

    @PrePersist
    public void beforeCreateOrder(Order order) {
        order.setPurchaseTime(LocalDateTime.now());
    }
}
