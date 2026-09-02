package com.io.banking.shared.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(name = "ErrorResponse", description = "Error response object")
public record ErrorResponse(
        @Schema(description = "HTTP status code", example = "400")
        int status,
        @Schema(description = "Error message", example = "Invalid request")
        String message,
        @Schema(description = "Error timestamp", example = "2024-01-01T10:00:00Z")
        Instant timestamp) {
    public ErrorResponse(int status, String message) {
        this(status, message, Instant.now());
    }
}

