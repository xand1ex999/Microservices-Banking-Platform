package com.io.banking.auth.controller;

import com.io.banking.auth.model.dto.AuthResponse;
import com.io.banking.auth.model.dto.LoginRequest;
import com.io.banking.auth.model.dto.RefreshRequest;
import com.io.banking.auth.model.dto.RegistrationRequest;
import com.io.banking.auth.service.AuthService;
import com.io.banking.auth.service.RefreshTokenService;
import com.io.banking.shared.security.model.AuthenticatedUser;
import com.io.banking.users.model.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal AuthenticatedUser user) {
        return "User ID: " + user.getId()
                + " Username: " + user.getUsername()
                + " Role: " + user.getAuthorities();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest request) {
        refreshTokenService.logout(request.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
