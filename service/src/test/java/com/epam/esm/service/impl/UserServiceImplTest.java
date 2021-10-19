package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserValidator userValidator;

    private static User user;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @BeforeEach
    public void initUseCase(){
        userService = new UserServiceImpl(userRepository, passwordEncoder, userValidator);
    }

    @BeforeAll
    public static void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("Nikita");
        user.setPassword("password");
    }

    @Test
    void findUserByIdPositiveResult() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));

        Optional<User> actualUser = userService.findUserById(1);

        assertEquals(actualUser, Optional.ofNullable(user));
    }

    @Test
    void findUserByUsernamePositiveResult() {
        when(userRepository.findByUsername("Nikita")).thenReturn(java.util.Optional.ofNullable(user));

        Optional<User> actualUser = userService.findByUsername("Nikita");

        assertTrue(actualUser.isPresent());
        assertEquals(actualUser.get().getUsername(), user.getUsername());
    }
}