package com.io.banking.shared.security.jwt.impl;

import com.io.banking.shared.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey signingKey;
    private final long accessExpiration;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.access.expiration}") long accessExpiration) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
    }

    @Override
    public String generateAccessToken(Long userId, String email, String role) {
        Date now = new Date();
        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role)
                .claim("email", email)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessExpiration))
                .signWith(signingKey)
                .compact();
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
