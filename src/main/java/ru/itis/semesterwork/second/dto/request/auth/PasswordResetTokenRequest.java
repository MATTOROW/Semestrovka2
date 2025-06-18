package ru.itis.semesterwork.second.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PasswordResetTokenRequest(
        @NotNull(message = "Email required")
        @Email
        String email
) {}
