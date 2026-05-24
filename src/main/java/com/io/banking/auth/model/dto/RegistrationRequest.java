package com.io.banking.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    @Size(min = 8, max = 64)
    private final String password;

    @NotBlank
    @Size(min = 2, max = 50)
    private final String name;
}
