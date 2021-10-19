package com.epam.esm.controller.link.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.entity.Tag;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLink implements LinkAdding<Tag> {

    private static final String CREATE_LINK = "create";
    private static final String UPDATE_LINK = "update";
    private static final String DELETE_LINK = "delete";

    @Override
    public void addLink(Tag entity) {
        if (entity!=null){
            Link linksSelf = linkTo(methodOn(TagController.class).getTag(entity.getId())).withSelfRel();
            Link linksCreate = linkTo(methodOn(TagController.class).createTag(entity)).withRel(CREATE_LINK);
            Link linksUpdate = linkTo(methodOn(TagController.class).updateTag(entity, entity.getId())).withRel(UPDATE_LINK);
            Link linksDelete = linkTo(methodOn(TagController.class).deleteTag(entity.getId())).withRel(DELETE_LINK);
            entity.add(linksSelf, linksCreate, linksUpdate, linksDelete);
        }
    }
}
