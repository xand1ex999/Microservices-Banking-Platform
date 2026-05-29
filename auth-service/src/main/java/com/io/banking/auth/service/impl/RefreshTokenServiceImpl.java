package com.io.banking.auth.service.impl;

import com.io.banking.auth.model.entity.RefreshToken;
import com.io.banking.auth.persistence.RefreshTokenRepository;
import com.io.banking.auth.service.RefreshTokenService;
import com.io.banking.shared.util.HashUtils;
import com.io.banking.users.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    private static final SecureRandom random = new SecureRandom();

    @Override
    @Transactional
    public String createRefreshToken(User user) {
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);

        String rawToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        var token = new RefreshToken();
        token.setUser(user);
        token.setTokenHash(HashUtils.sha256(rawToken));
        token.setExpiresAt(Instant.now().plusSeconds(7L * 24 * 60 * 60));
        token.setRevoked(false);
        repository.save(token);
        return rawToken;
    }

    @Override
    @Transactional
    public void logout(String rawRefreshToken) {
        String hash = HashUtils.sha256(rawRefreshToken);
        repository.findByTokenHash(hash)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    repository.save(token);
                    log.info("Refresh token revoked for userId={}", token.getUser().getId());
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByTokenHash(String hash) {
        return repository.findByTokenHash(hash);
    }
}
