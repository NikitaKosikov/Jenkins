package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    String GET_MOST_WIDELY_USED_TAG = "SELECT tags AS id, tag_name AS name FROM (SELECT tags.id AS tag_id, tags.name AS tag_name, COUNT(tag_id) AS tag_count FROM tags " +
            "JOIN m2m_gift_certificates_tags m2mgc ON tags.id = m2mgc.tag_id " +
            "JOIN gift_certificates gc ON m2mgc.gift_certificate_id = gc.id " +
            "JOIN orders o ON gc.id = o.gift_certificate_id " +
            "JOIN (SELECT SUM(price) AS orders_cost, user_id AS ui FROM orders GROUP BY user_id) " +
            "AS a ON o.user_id = a.ui WHERE orders_cost = " +
            "(SELECT SUM(price) AS orders_cost FROM orders GROUP BY user_id ORDER BY orders_cost DESC LIMIT 1)" +
            "GROUP BY tag_id ORDER BY tag_count DESC LIMIT 1 AS b";

    String FIND_ALL_BY_CERTIFICATE_ID = "SELECT t.id, t.name FROM m2m_gift_certificates_tags " +
            " JOIN tags t on t.id = m2m_gift_certificates_tags.tag_id " +
            " WHERE gift_certificate_id = :gift_certificate_id";

    Optional<Tag> findByName(String name);

    @Query(value = GET_MOST_WIDELY_USED_TAG, nativeQuery = true)
    Optional<Tag> getMostUsedTagOfUserWithHighestCostOfOrders();

    @Query(value = FIND_ALL_BY_CERTIFICATE_ID, nativeQuery = true)
    List<Tag> readByCertificateId(@Param("gift_certificate_id") long id);
}
