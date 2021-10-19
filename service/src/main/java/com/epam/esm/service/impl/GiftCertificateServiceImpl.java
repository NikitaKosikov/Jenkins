package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.query.GiftCertificateSearchByParameters;
import com.epam.esm.query.SearchSpecification;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.sort.SortFactory;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String GIFT_CERTIFICATE_COLUMN_NAME_DB="name";
    private static final String GIFT_CERTIFICATE_COLUMN_DESCRIPTION_DB="description";



    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagValidator tagValidator;
    private final TagService tagService;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      GiftCertificateValidator giftCertificateValidator,
                                      TagValidator tagValidator, TagService tagService) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagValidator = tagValidator;
        this.tagService = tagService;
    }


    @Override
    @Transactional
    public GiftCertificate insert(GiftCertificate giftCertificate, Tag newTag){
        giftCertificateValidator.validate(giftCertificate);

        List<Tag> tags = new ArrayList<>();

        if (!"".equals(newTag.getName()) && newTag.getName() != null) {
            Optional<Tag> tag = tagService.findTagByName(newTag.getName());
            if (tag.isPresent()){
                tag = Optional.ofNullable(tagService.insert(newTag));
            }
            tag.ifPresent(tags::add);
        }

        giftCertificate.setTags(tags);

        return giftCertificateRepository.save(giftCertificate);
     }

    @Override
    @Transactional
    public void update(GiftCertificate giftCertificate){

        giftCertificateValidator.validate(giftCertificate);

        Optional<GiftCertificate> oldGiftCertificate = readById(giftCertificate.getId());
        if (oldGiftCertificate.isPresent()) {
            giftCertificate = updateFields(oldGiftCertificate.get(), giftCertificate);
        }

        giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public void delete(long id) {
        giftCertificateRepository.deleteById(id);
    }

    @Override
    public Page<GiftCertificate> read(Pageable pageable, List<String> tagNames, String certificateName, String certificateDescription, List<String> sort) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put(GIFT_CERTIFICATE_COLUMN_NAME_DB, certificateName);
        conditions.put(GIFT_CERTIFICATE_COLUMN_DESCRIPTION_DB, certificateDescription);

        GiftCertificateSearchByParameters giftCertificateSearchByParameters = new GiftCertificateSearchByParameters();
        giftCertificateSearchByParameters.setConditions(conditions);
        giftCertificateSearchByParameters.setTagNames(tagNames);

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), SortFactory.createSort(sort));
        return giftCertificateRepository.findAll(new SearchSpecification<>(giftCertificateSearchByParameters), pageable);
    }

    @Override
    public Optional<GiftCertificate> readById(long id) {
        return giftCertificateRepository.findById(id);
    }

    @Override
    @Transactional
    public void addTag(long certificateId, Tag newTag){
        tagValidator.validateName(newTag.getName());
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(certificateId);
        if (!"".equals(newTag.getName())) {
            Optional<Tag> tag = tagService.findTagByName(newTag.getName());
            if (tag.isPresent()){
                tag = Optional.ofNullable(tagService.insert(newTag));
            }
            if (giftCertificate.isPresent()) {
                tag.ifPresent(giftCertificate.get()::addTag);
            }
        }
        giftCertificate.ifPresent(giftCertificateRepository::save);
    }

    @Override
    @Transactional
    public void updateCertificateTagById(long certificateId, long tagId, String tagName){
        tagValidator.validateName(tagName);
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(certificateId);

        if (!"".equals(tagName)) {
            Optional<Tag> tag = tagService.findTagByName(tagName);
            if (tag.isPresent()){
                tagService.insert(new Tag(tagName));
            }
        }
        if (giftCertificate.isPresent()){
            for (Tag tag : giftCertificate.get().getTags()) {
                if (tag.getId() == tagId){
                    tag.setName(tagName);
                }
            }
        }
        giftCertificate.ifPresent(giftCertificateRepository::save);
    }

    @Override
    @Transactional
    public void deleteCertificateTagById(long certificateId, long tagId) {
        giftCertificateRepository.deleteCertificateTagById(certificateId, tagId);
    }

    @Override
    @Transactional
    public void updatePrice(long certificateId, BigDecimal price) {
        giftCertificateValidator.isValidPrice(price);
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(certificateId);
        if (giftCertificate.isPresent()){
            giftCertificate.get().setPrice(price);
            giftCertificateRepository.save(giftCertificate.get());
        }
    }

    private GiftCertificate updateFields(GiftCertificate oldGiftCertificate, GiftCertificate newGiftCertificate){
        if (newGiftCertificate.getName()!=null){
            giftCertificateValidator.isValidName(newGiftCertificate.getName());
            oldGiftCertificate.setName(newGiftCertificate.getName());
        }
        if (newGiftCertificate.getDescription()!=null){
            oldGiftCertificate.setDescription(newGiftCertificate.getDescription());
        }
        if (newGiftCertificate.getPrice()!=null){
            giftCertificateValidator.isValidPrice(newGiftCertificate.getPrice());
            oldGiftCertificate.setPrice(newGiftCertificate.getPrice());
        }
        if (newGiftCertificate.getDuration()!=0){
            giftCertificateValidator.isValidDuration(newGiftCertificate.getDuration());
            oldGiftCertificate.setDuration(newGiftCertificate.getDuration());
        }
        if (newGiftCertificate.getTags()!=null){
            oldGiftCertificate.setTags(newGiftCertificate.getTags());
        }
        return oldGiftCertificate;
    }
}
