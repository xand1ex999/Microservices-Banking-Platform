package com.io.banking.shared.security.jwt;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String generateAccessToken(Long userId, String email, String role);

    Claims parseToken(String token);

}
