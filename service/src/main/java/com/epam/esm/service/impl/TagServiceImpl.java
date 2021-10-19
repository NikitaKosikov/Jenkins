package com.epam.esm.service.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagValidator tagValidator) {
        this.tagRepository = tagRepository;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public Tag insert(Tag tag) {
        tagValidator.validateName(tag.getName());
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void update(Tag tag, long id) {
        tagValidator.validateName(tag.getName());

        Optional<Tag> oldTag = readById(id);
        if (tag.getName()!=null && oldTag.isPresent()){
            oldTag.get().setName(tag.getName());
        }
        oldTag.ifPresent(value -> value.setId(id));
        oldTag.ifPresent(tagRepository::save);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional<Tag> tag = readById(id);
        tag.ifPresent(tagRepository::delete);
    }

    @Override
    public Page<Tag> read(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Optional<Tag> readById(long id) {
        return tagRepository.findById(id);
    }

    @Override
    public List<Tag> readByCertificateId(long id) {
        return tagRepository.readByCertificateId(id);
    }

    @Override
    public Optional<Tag> findTagByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    @Override
    public Optional<Tag> getMostUsedTagOfUserWithHighestCostOfOrders() {
        return tagRepository.getMostUsedTagOfUserWithHighestCostOfOrders();
    }
}
