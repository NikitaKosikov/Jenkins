package com.epam.esm.security.filter;

import com.epam.esm.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final static String USERNAME_PARAMETER = "username";
    private final static String PASSWORD_PARAMETER = "password";
    private final static String ACCESS_TOKEN_IN_HEADER = "access_token";

    public CustomAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtTokenProvider.createToken(user);
        response.setHeader(ACCESS_TOKEN_IN_HEADER, token);
    }
}
