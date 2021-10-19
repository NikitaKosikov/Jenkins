package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidNameOfCertificate;
import com.epam.esm.exception.InvalidPrice;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private GiftCertificateValidator giftCertificateValidator;

    private GiftCertificateService giftCertificateService;

    @Mock
    private static TagValidator tagValidator;

    @Mock
    private static TagService tagService;


    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificate giftCertificate4;

    @BeforeEach
    public void initUseCase(){
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, giftCertificateValidator,
                tagValidator, tagService);
    }

    @BeforeAll
    public static void setUp() {
        giftCertificate1 = new GiftCertificate(1L, "name", "nameewq", new BigDecimal("33.33"),
                31, LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0));
        giftCertificate2 = new GiftCertificate(1L, "name", "nameewq", new BigDecimal("33.333"),
                31, LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0));
        giftCertificate4 = new GiftCertificate(1L, "Travel to German", "You will like it", new BigDecimal(100),
                10, LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0));
    }

    @Test
    void insertGiftCertificatePositiveResult() {
        when(giftCertificateRepository.save(giftCertificate1)).thenReturn(giftCertificate1);

        GiftCertificate actualCertificate = giftCertificateRepository.save(giftCertificate1);

        assertEquals(actualCertificate, giftCertificate1);
    }

    @Test
    void insertGiftCertificatePriceThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(new BigDecimal("33.333"));

        doThrow(new InvalidPrice()).when(giftCertificateValidator)
                .validate(giftCertificate);

        assertThrows(InvalidPrice.class,
                () -> giftCertificateService.insert(giftCertificate, any(Tag.class)));
    }

    @Test
    void insertGiftCertificateEmptyNameThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("");

        doThrow(new InvalidNameOfCertificate()).when(giftCertificateValidator)
                .validate(giftCertificate);

        assertThrows(InvalidNameOfCertificate.class,
                () -> giftCertificateService.insert(giftCertificate, any(Tag.class)));
    }

    @Test
    void insertGiftCertificateNameNullThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(null);

        doThrow(new InvalidNameOfCertificate()).when(giftCertificateValidator)
                .validate(giftCertificate);

        assertThrows(InvalidNameOfCertificate.class,
                () -> giftCertificateService.insert(giftCertificate, any(Tag.class)));
    }

    @Test
    void updateGiftCertificatePositiveResult() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setPrice(new BigDecimal("33.33"));
        giftCertificate.setName("nameUpdateTest");
        giftCertificate.setDescription("nameewq");
        giftCertificate.setDuration(31);
        giftCertificate.setCreateDate(LocalDateTime.of(2021,6, 24, 17,58));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021,6, 24, 17,58));

        doNothing().when(giftCertificateValidator).validate(giftCertificate);
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);

        giftCertificateService.update(giftCertificate);

        assertEquals(giftCertificate.getName(), "nameUpdateTest");

    }

    @Test
    void updateGiftCertificatePriceThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setPrice(new BigDecimal("33.333"));
        giftCertificate.setName("name");
        giftCertificate.setDescription("nameewq");
        giftCertificate.setDuration(31);
        giftCertificate.setCreateDate(LocalDateTime.of(2021,6, 24, 17,58));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021,6, 24, 17,58));

        doThrow(new InvalidPrice()).when(giftCertificateValidator)
                .validate(giftCertificate);

        assertThrows(InvalidPrice.class,
                () -> giftCertificateService.update(giftCertificate));
    }

    @Test
    void updateGiftCertificateEmptyNameThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setPrice(new BigDecimal("33.33"));
        giftCertificate.setName("");
        giftCertificate.setDescription("nameewq");
        giftCertificate.setDuration(31);
        giftCertificate.setCreateDate(LocalDateTime.of(2021,6, 24, 17,58));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021,6, 24, 17,58));

        doThrow(new InvalidNameOfCertificate()).when(giftCertificateValidator)
                .validate(giftCertificate);

        assertThrows(InvalidNameOfCertificate.class,
                () -> giftCertificateService.update(giftCertificate));
    }

    @Test
    void updateGiftCertificateNameNullThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setPrice(new BigDecimal("33.33"));
        giftCertificate.setName(null);
        giftCertificate.setDescription("nameewq");
        giftCertificate.setDuration(31);
        giftCertificate.setCreateDate(LocalDateTime.of(2021,6, 24, 17,58));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021,6, 24, 17,58));

        doThrow(new InvalidNameOfCertificate()).when(giftCertificateValidator)
                .validate(giftCertificate);

        assertThrows(InvalidNameOfCertificate.class,
                () -> giftCertificateService.update(giftCertificate));
    }

    @Test
    void deleteGiftCertificatePositiveResult() {
        final long id = 1;
        doNothing().when(giftCertificateRepository).deleteById(id);
        assertDoesNotThrow(() -> giftCertificateService.delete(id));
    }

    @Test
    void readGiftCertificateByIdPositiveResult() {
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(giftCertificate4));
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateService.readById(1);
        assertEquals(foundGiftCertificate, Optional.ofNullable(giftCertificate4));
    }

    @Test
    void deleteCertificateTagById() {
        doNothing().when(giftCertificateRepository).deleteCertificateTagById(anyLong(), anyLong());
        giftCertificateRepository.deleteCertificateTagById(anyInt(), anyInt());
        verify(giftCertificateRepository, times(1))
                .deleteCertificateTagById(0, 0);
    }

    @Test
    void updatePricePositiveResult() {
        BigDecimal bigDecimal = new BigDecimal("33.33");

        doNothing().when(giftCertificateValidator).isValidPrice(bigDecimal);
        when(giftCertificateRepository.findById(giftCertificate2.getId())).thenReturn(Optional.ofNullable(giftCertificate2));

        giftCertificate2.setPrice(bigDecimal);

        when(giftCertificateRepository.save(giftCertificate2)).thenReturn(giftCertificate2);

        giftCertificateService.updatePrice(1L, bigDecimal);

        assertEquals(giftCertificate2.getPrice(), bigDecimal);

    }
}