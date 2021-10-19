package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    /**
     * Create giftCertificate and tag if the tag not exist.
     *
     * @param giftCertificate the gift certificate.
     * @param newTag the tag for gift certificate.
     */
    GiftCertificate insert(GiftCertificate giftCertificate, Tag newTag);

    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate that will be create.
     *
     */
    void update(GiftCertificate giftCertificate);

    /**
     * Delete the gift certificate by specific id.
     *
     * @param id the id of the gift certificate.
     */
    void delete(long id);

    /**
     * Looking for a set of gift certificates.
     *
     * @param pageable the contains information about the data selection
     * @param tagNames the list of names of tags for which we are going to filter
     * @param certificateName the name of certificate for which we are going to filter
     * @param certificateDescription the description of certificate for which we are going to filter
     * @param sort the list of data in which the sorting is specified
     * @return list of found gift certificates.
     */
    Page<GiftCertificate> read(Pageable pageable, List<String> tagNames, String certificateName, String certificateDescription, List<String> sort);

    /**
     * Looking for the gift certificate by specific id.
     *
     * @param certificateId the id of gift certificate.
     * @return the found gift certificate.
     */
    Optional<GiftCertificate> readById(long certificateId);

    /**
     * Adding tag to gift certificate.
     *
     * @param certificateId the id of the tag.
     * @param newTag the tag which will be create.
     */
    void addTag(long certificateId, Tag newTag);

    /**
     * Update tag of gift certificate
     *
     * @param certificateId the id of gift certificate that we are updating the tag for
     * @param tagId the id of the tag to be changed
     * @param tagName the new name of tag
     */
    void updateCertificateTagById(long certificateId, long tagId, String tagName);

    /**
     * Delete tag of gift certificate
     *
     * @param certificateId the id of gift certificate that we are deleting the tag for
     * @param tagId the id of the tag to be deleted
     */
    void deleteCertificateTagById(long certificateId, long tagId);

    /**
     * Update price of gift certificate
     *
     * @param certificateId the id of gift certificate
     * @param price the new price
     */
    void updatePrice(long certificateId, BigDecimal price);
}
