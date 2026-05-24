package com.io.banking.auth.service.impl;

import com.io.banking.auth.event.UserRegisteredEvent;
import com.io.banking.auth.model.dto.AuthResponse;
import com.io.banking.auth.model.dto.LoginRequest;
import com.io.banking.auth.model.dto.RegistrationRequest;
import com.io.banking.auth.model.entity.RefreshToken;
import com.io.banking.auth.service.AuthService;
import com.io.banking.auth.service.RefreshTokenService;
import com.io.banking.shared.exception.InvalidCredentialsException;
import com.io.banking.shared.exception.UserAlreadyExistsException;
import com.io.banking.shared.security.jwt.JwtService;
import com.io.banking.shared.util.HashUtils;
import com.io.banking.users.mapper.UserMapper;
import com.io.banking.users.model.dto.UserResponse;
import com.io.banking.users.model.entity.User;
import com.io.banking.users.model.enums.Role;
import com.io.banking.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public UserResponse register(RegistrationRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User with this email " + dto.getEmail() + " already exists");
        }

        var user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
        user.setActive(true);

        User saved = userRepository.save(user);
        eventPublisher.publishEvent(new UserRegisteredEvent(this, saved.getId(), saved.getEmail()));
        return UserMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "No user with email: " + dto.getEmail()));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Wrong password");
        }

        String accessToken = jwtService.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse refreshToken(String rawRefreshToken) {
        String hash = HashUtils.sha256(rawRefreshToken);
        RefreshToken tokenEntity = refreshTokenService.findByTokenHash(hash)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid refresh token"));

        if (tokenEntity.isRevoked() || tokenEntity.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidCredentialsException("Refresh token expired or revoked");
        }

        User user = tokenEntity.getUser();
        String newAccessToken = jwtService.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());
        return new AuthResponse(newAccessToken, rawRefreshToken);
    }
}
