package com.epam.esm.security;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    private static final String AUTHORITIES_IN_TOKEN = "authorities";

    @Autowired
    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(org.springframework.security.core.userdetails.User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(AUTHORITIES_IN_TOKEN, user.getAuthorities());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()){
            UserDetails userDetails = UserDetailsFactory.create(user.get());
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        return null;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public long getValidityInMilliseconds() {
        return validityInMilliseconds;
    }
}