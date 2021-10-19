package com.epam.esm.controller;

import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/gift-certificates")
@PreAuthorize("hasRole('ADMIN')")
public class GiftCertificateController {

    private final LinkAdding<GiftCertificate> linkAdding;

    private final GiftCertificateService giftCertificateService;

    private final TagService tagService;

    @Autowired
    public GiftCertificateController(LinkAdding<GiftCertificate> linkAdding, GiftCertificateService giftCertificateService, TagService tagService) {
        this.linkAdding = linkAdding;
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<GiftCertificate>> getAllGiftCertificates(Pageable pageable,
                                                                        @RequestParam(value = "tag_name", required = false, defaultValue = "") List<String> tagNames,
                                                                        @RequestParam(value = "certificate_name", required = false) String certificateName,
                                                                        @RequestParam(value = "certificate_description", required = false) String certificateDescription,
                                                                        @RequestParam(value = "sort", required = false) List<String> sort){
        Page<GiftCertificate> giftCertificates = giftCertificateService.read(pageable,tagNames, certificateName, certificateDescription, sort);
        giftCertificates.forEach(linkAdding::addLink);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GiftCertificate> createGiftCertificate(@RequestBody GiftCertificate giftCertificate,
                                        @RequestParam(value = "tagName", required = false) String tagName){
        Tag tag = new Tag(tagName);
        giftCertificate = giftCertificateService.insert(giftCertificate, tag);
        linkAdding.addLink(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
    }

    @GetMapping("/{certificateId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Optional<GiftCertificate>> getGiftCertificate(@PathVariable long certificateId){
        Optional<GiftCertificate> giftCertificate = giftCertificateService.readById(certificateId);
        giftCertificate.ifPresent(linkAdding::addLink);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    @PutMapping("/{certificateId}")
    public ResponseEntity<Void> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate,
                                                @PathVariable long certificateId){
        giftCertificate.setId(certificateId);
        giftCertificateService.update(giftCertificate);
        linkAdding.addLink(giftCertificate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable long certificateId){
        giftCertificateService.delete(certificateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{certificateId}/tags")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Tag>> getTagsOfCertificate(@PathVariable long certificateId){
        List<Tag> tags = tagService.readByCertificateId(certificateId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping("/{certificateId}/tags")
    public ResponseEntity<Void> addTagToCertificate(@PathVariable long certificateId,@RequestBody Tag tag){
        giftCertificateService.addTag(certificateId, tag);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{certificateId}/tags/{tagId}")
    public ResponseEntity<Void> updateTagOfGiftCertificate(@PathVariable long certificateId, @PathVariable long tagId, @RequestParam String tagName) {
        giftCertificateService.updateCertificateTagById(certificateId,tagId, tagName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{certificateId}/tags/{tagId}")
    public ResponseEntity<Void> deleteTagOfGiftCertificate(@PathVariable long certificateId, @PathVariable long tagId){
        giftCertificateService.deleteCertificateTagById(certificateId,tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{certificateId}/price")
    public ResponseEntity<Void> updatePriceFromGiftCertificate(@PathVariable long certificateId, @RequestParam BigDecimal price){
        giftCertificateService.updatePrice(certificateId, price);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
