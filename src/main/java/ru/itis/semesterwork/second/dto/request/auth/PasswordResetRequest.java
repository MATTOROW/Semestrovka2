package ru.itis.semesterwork.second.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PasswordResetRequest(
        @NotNull(message = "Email required")
        @Email
        String email,

        @NotNull(message = "Code must be present")
        String code,

        @NotNull
        @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
        String newPassword
) {}
