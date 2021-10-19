package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidNameOfTag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagValidator tagValidator;

    @Mock
    private TagService tagService;

    private static Tag tag1;
    private static Tag tag2;
    private static GiftCertificate giftCertificate;

    @BeforeEach
    public void initUseCase(){
        tagService = new TagServiceImpl(tagRepository, tagValidator);
    }

    @BeforeAll
    public static void setUp() {
        tag1 = new Tag("TestName");
        tag1.setId(1L);
        tag2 = new Tag("");
        tag2.setId(2L);
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(5L);
        giftCertificate.setTags(List.of(tag1, tag2));
    }

    @Test
    void insertPositiveResult() {
        when(tagRepository.save(tag1)).thenReturn(tag1);
        Tag foundTag = tagRepository.save(tag1);
        assertEquals(foundTag, tag1);
    }

    @Test
    void insertNegativeResult() {
        doThrow(InvalidNameOfTag.class).when(tagValidator).validateName(new Tag("").getName());

        assertThrows(InvalidNameOfTag.class,
                () -> tagService.insert(tag2));
    }

    @Test
    void updateTagExistPositiveResult() {
        final long id = 3L;
        Tag tag = new Tag("TestName");
        tag.setId(id);

        doNothing().when(tagValidator).validateName(tag.getName());

        assertDoesNotThrow(() -> tagService.update(tag, id));
    }

    @Test
    void updateTagNotExistNegativeResult() {
        final long id = 3L;
        Tag tag = new Tag("testName");
        Optional<Tag> tag3 = Optional.of(tag);

        doNothing().when(tagValidator).validateName(tag.getName());
        when(tagService.readById(id)).thenReturn(tag3);
        when(tagRepository.save(tag3.get())).thenReturn(tag3.get());

        assertDoesNotThrow(() -> tagService.update(tag, id));
    }

    @Test
    void deleteTagPositiveResult() {
        final long id = 1;
        doNothing().when(tagRepository).deleteById(id);
        assertDoesNotThrow(() -> tagRepository.deleteById(id));
    }

    @Test
    void readById() {
        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.of(tag1));

        Optional<Tag> fountTag = tagService.readById(1L);

        assertEquals(fountTag, Optional.ofNullable(tag1));
    }

    @Test
    void readByCertificateId() {
        when(tagRepository.readByCertificateId(5)).thenReturn(giftCertificate.getTags());
        List<Tag> foundTags = tagService.readByCertificateId(5);

        assertEquals(foundTags.size(), giftCertificate.getTags().size());
    }
}