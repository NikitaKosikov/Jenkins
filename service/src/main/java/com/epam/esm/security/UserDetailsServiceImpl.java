package com.epam.esm.security;

import com.epam.esm.dto.TokenDto;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserDetailsServiceImpl(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenDto signUp(User user){
        user = userService.save(user);
        UserDetails userDetails = UserDetailsFactory.create(user);
        String token = jwtTokenProvider.createToken((org.springframework.security.core.userdetails.User) userDetails);
        return new TokenDto(user.getUsername(), token, jwtTokenProvider.getValidityInMilliseconds());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()){
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                username,
                user.get().getPassword(),
                authorities);
    }

}
