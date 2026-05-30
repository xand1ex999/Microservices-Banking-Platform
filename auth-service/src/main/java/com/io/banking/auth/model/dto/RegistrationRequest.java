package com.io.banking.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "RegistrationRequest", description = "User registration data")
public class RegistrationRequest {

    @Email
    @NotBlank
    @Schema(description = "User email address", example = "user@example.com")
    private final String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "User password (8-64 characters)", example = "securePassword123")
    private final String password;

    @NotBlank
    @Size(min = 2, max = 50)
    @Schema(description = "User full name (2-50 characters)", example = "John Doe")
    private final String name;
}

