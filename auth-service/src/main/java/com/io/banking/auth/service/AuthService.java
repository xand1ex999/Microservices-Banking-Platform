package com.io.banking.auth.service;

import com.io.banking.auth.model.dto.AuthResponse;
import com.io.banking.auth.model.dto.LoginRequest;
import com.io.banking.auth.model.dto.RegistrationRequest;
import com.io.banking.users.model.dto.UserResponse;

public interface AuthService {

    UserResponse register(RegistrationRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(String rawRefreshToken);
}
