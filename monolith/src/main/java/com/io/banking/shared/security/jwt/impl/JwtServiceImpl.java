package com.io.banking.shared.security.jwt.impl;

import com.io.banking.shared.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Override
    public String generateAccessToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
