package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    String DELETE_CERTIFICATE_TAG_BY_ID = "DELETE FROM m2m_gift_certificates_tags WHERE gift_certificate_id = :gift_certificate_id AND  tag_id = :tag_id";

    @Query(value = DELETE_CERTIFICATE_TAG_BY_ID, nativeQuery = true)
    void deleteCertificateTagById(@Param("gift_certificate_id") long certificateId, @Param("tag_id") long tagId);
}
