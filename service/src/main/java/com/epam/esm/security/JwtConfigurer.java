package com.epam.esm.security;


import com.epam.esm.security.filter.CustomAuthenticationFilter;
import com.epam.esm.security.filter.CustomAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(jwtTokenProvider, authenticationManager);
        CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter(jwtTokenProvider);
        httpSecurity.addFilter(authenticationFilter);
        httpSecurity.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}