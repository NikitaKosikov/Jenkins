package com.epam.esm.controller.link.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLink implements LinkAdding<Order> {

    private final GiftCertificateLink addingGiftCertificateLink;
    private final UserLink addingUserLink;

    private static final String CREATE_LINK = "create";

    @Autowired
    public OrderLink(GiftCertificateLink addingGiftCertificateLink, UserLink addingUserLink) {
        this.addingGiftCertificateLink = addingGiftCertificateLink;
        this.addingUserLink = addingUserLink;
    }

    @Override
    public void addLink(Order entity) {
        if (entity!=null){
            Link linksSelf = linkTo(methodOn(OrderController.class).findOrderById(entity.getId())).withSelfRel();
            Link linksCreate = linkTo(methodOn(OrderController.class).makePurchase(entity)).withRel(CREATE_LINK);
            addingGiftCertificateLink.addLink(entity.getGiftCertificate());
            addingUserLink.addLink(entity.getUser());
            entity.add(linksSelf, linksCreate);
        }

    }
}
