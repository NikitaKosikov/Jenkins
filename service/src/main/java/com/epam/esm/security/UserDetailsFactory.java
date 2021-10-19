package com.epam.esm.security;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsFactory {

    private static final String ROLE_PREFIX = "ROLE_";

    public static UserDetails create(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRole())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole.name()));
        return authorities;
    }
}
