package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

import static com.project.bankproject.domain.util.ApiConstants.SECRET_KEY;

/**
 * class TokenServiceImpl implementation of {@link TokenService} interface.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String generateToken(User user) {
        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000000L))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }

    @Override
    public String validateTokenAndGetLogin(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        if (claims == null || claims.getSubject() == null) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        String login = claims.getSubject();
        return login;
    }
}
