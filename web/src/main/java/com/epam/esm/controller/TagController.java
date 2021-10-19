package com.epam.esm.controller;

import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private final LinkAdding<Tag> linkAddingTag;

    private final TagService tagService;

    @Autowired
    public TagController(LinkAdding<Tag> linkAddingTag, TagService tagService) {
        this.linkAddingTag = linkAddingTag;
        this.tagService = tagService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<Tag>> getAllTags(Pageable pageable){
        Page<Tag> tags = tagService.read(pageable);
        tags.forEach(linkAddingTag::addLink);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        tag = tagService.insert(tag);
        linkAddingTag.addLink(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Optional<Tag>> getTag(@PathVariable long id){
        Optional<Tag> tag = tagService.readById(id);
        tag.ifPresent(linkAddingTag::addLink);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateTag(@RequestBody Tag tag, @PathVariable long id){
        tagService.update(tag, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable long id){
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/popular-tag")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Optional<Tag>> getMostUsedTagOfUserWithHighestCostOfOrders(){
        Optional<Tag> tag = tagService.getMostUsedTagOfUserWithHighestCostOfOrders();
        tag.ifPresent(linkAddingTag::addLink);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }
}
