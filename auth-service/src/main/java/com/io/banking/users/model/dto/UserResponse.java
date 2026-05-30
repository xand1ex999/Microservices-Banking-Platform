package com.io.banking.users.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserResponse", description = "User account information")
public class UserResponse {
    @Schema(description = "User unique identifier", example = "1")
    private Long id;

    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    @Schema(description = "User full name", example = "John Doe")
    private String name;

    @Schema(description = "User role", example = "USER")
    private String role;

    @Schema(description = "User account active status", example = "true")
    private Boolean active;

    @Schema(description = "Account creation timestamp", example = "2024-01-01T10:00:00")
    private LocalDateTime createdAt;
}

