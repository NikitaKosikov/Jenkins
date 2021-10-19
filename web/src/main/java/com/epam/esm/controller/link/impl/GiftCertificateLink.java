package com.epam.esm.controller.link.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateLink implements LinkAdding<GiftCertificate> {
    private final TagLink addingTagLink;

    private static final String CREATE_LINK = "create";
    private static final String UPDATE_LINK = "update";
    private static final String DELETE_LINK = "delete";

    @Autowired
    public GiftCertificateLink(TagLink addingTagLink) {
        this.addingTagLink = addingTagLink;
    }

    @Override
    public void addLink(GiftCertificate entity) {
        if (entity!=null){
            Link linksSelf = linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(entity.getId())).withSelfRel();
            Link linksCreate = linkTo(methodOn(GiftCertificateController.class).createGiftCertificate(entity, "tagName")).withRel(CREATE_LINK);
            Link linksUpdate = linkTo(methodOn(GiftCertificateController.class).updateGiftCertificate(entity, entity.getId())).withRel(UPDATE_LINK);
            Link linksDelete = linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(entity.getId())).withRel(DELETE_LINK);
            entity.getTags().forEach(addingTagLink::addLink);
            entity.add(linksSelf, linksCreate, linksUpdate, linksDelete);
        }
    }
}
