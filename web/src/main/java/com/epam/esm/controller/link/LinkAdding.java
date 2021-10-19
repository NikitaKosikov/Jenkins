package com.epam.esm.controller.link;

import org.springframework.hateoas.RepresentationModel;

public interface LinkAdding<T extends RepresentationModel<T>> {
    void addLink(T entity);
}
