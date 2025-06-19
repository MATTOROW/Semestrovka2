package ru.itis.semesterwork.second.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос токена для сброса пароля")
public record PasswordResetTokenRequest(
        @Schema(description = "Email аккаунта", example = "user@example.com")
        @NotNull(message = "Email required")
        @Email
        String email
) {}

