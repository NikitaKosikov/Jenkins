package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TagService {

    /**
     * Create new tag
     *
     * @param tag the tag which will be created
     * @return created tag
     */
    Tag insert(Tag tag);

    /**
     * Update tag
     *
     * @param tag the new tag which will be created
     * @param id the id of tag which should be updated
     */
    void update(Tag tag, long id);

    /**
     * Delete tag by id
     *
     * @param id the id of tag which will be delete
     */
    void delete(long id);

    /**
     * Looking for all tags
     *
     * @param pageable the contains information about the data selection
     * @return the found list of tags
     */
    Page<Tag> read(Pageable pageable);

    /**
     * Looking for tag by id
     *
     * @param id the id of tag
     * @return the found tag
     */
    Optional<Tag> readById(long id);

    /**
     * Looking for  tag by name
     *
     * @param tagName the name of tag by which we are looking for tag
     * @return the found tag
     */
    Optional<Tag> findTagByName(String tagName);

    /**
     * Looking for  all tags by specific gift certificate
     * @param id the id of gift certificate
     * @return the found list of tags
     */
    List<Tag> readByCertificateId(long id);

    /**
     * Get most used tag of user with highest cost of orders
     *
     * @return found tag
     */
    Optional<Tag> getMostUsedTagOfUserWithHighestCostOfOrders();
}
