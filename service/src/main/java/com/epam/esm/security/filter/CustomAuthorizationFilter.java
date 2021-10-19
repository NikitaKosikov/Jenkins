package com.epam.esm.security.filter;

import com.epam.esm.security.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_END_POINT = "/login";
    private static final String SIGN_UP_END_POINT = "/users/signup";

    public CustomAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (requireAuthorization(request)){
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                String token = authorizationHeader.substring("Bearer ".length());
                jwtTokenProvider.validateToken(token);
                Authentication authenticationToken = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean requireAuthorization(HttpServletRequest request){
        return !request.getServletPath().equals(LOGIN_END_POINT) || request.getServletPath().equals(SIGN_UP_END_POINT);
    }
}
