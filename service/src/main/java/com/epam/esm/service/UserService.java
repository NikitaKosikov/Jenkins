package com.epam.esm.service;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    /**
     * Looking for all users
     * @param pageable the contains information about the data selection
     * @return the found list of users
     */
    Page<User> findAllUsers(Pageable pageable);

    /**
     * Find user by username
     *
     * @param username the name of user that will be found
     * @return user
     */
    Optional<User> findByUsername(String username);

    /**
     * Looking for user by id
     *
     * @param id the id of user
     * @return found user
     */
    Optional<User> findUserById(long id);

    /**
     * Create new user
     *
     * @param user the user that will be created
     * @return new user
     */
    User save(User user);
}
