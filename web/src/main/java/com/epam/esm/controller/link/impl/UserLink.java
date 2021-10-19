package com.epam.esm.controller.link.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.entity.User;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLink implements LinkAdding<User> {

    private static final String CREATE_LINK = "create";
    @Override
    public void addLink(User entity) {
        if (entity!=null){
            Link linksSelf = linkTo(methodOn(UserController.class).findUserById(entity.getId())).withSelfRel();
            entity.add(linksSelf);
        }
    }
}
