package com.io.banking.auth.service;

import com.io.banking.auth.model.entity.RefreshToken;
import com.io.banking.users.model.entity.User;

import java.util.Optional;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    void logout(String rawRefreshToken);

    Optional<RefreshToken> findByTokenHash(String hash);

}
