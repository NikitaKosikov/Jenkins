package com.epam.esm.controller;

import com.epam.esm.controller.link.LinkAdding;
import com.epam.esm.dto.TokenDto;
import com.epam.esm.entity.User;
import com.epam.esm.security.UserDetailsServiceImpl;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

    private final LinkAdding<User> linkAdding;
    private final UserDetailsServiceImpl userDetailService;

    private final UserService userService;

    @Autowired
    public UserController(LinkAdding<User> linkAdding, UserDetailsServiceImpl userDetailService, UserService userService) {
        this.linkAdding = linkAdding;
        this.userDetailService = userDetailService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAllUsers(Pageable pageable){
        Page<User> users = userService.findAllUsers(pageable);
        users.forEach(linkAdding::addLink);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable long id){
        Optional<User> user = userService.findUserById(id);
        user.ifPresent(linkAdding::addLink);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenDto> signup(@RequestBody User user) {
        TokenDto tokenDto = userDetailService.signUp(user);
        return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
    }
}
