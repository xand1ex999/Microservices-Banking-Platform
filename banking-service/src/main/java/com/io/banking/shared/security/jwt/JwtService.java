package com.io.banking.shared.security.jwt;

import io.jsonwebtoken.Claims;

public interface JwtService {
    Claims parseToken(String token);
}
