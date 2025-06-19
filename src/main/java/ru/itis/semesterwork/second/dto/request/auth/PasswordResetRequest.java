package ru.itis.semesterwork.second.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на сброс пароля")
public record PasswordResetRequest(
        @Schema(description = "Email аккаунта", example = "user@example.com")
        @NotNull(message = "Email required")
        @Email
        String email,

        @Schema(description = "Код подтверждения", example = "123456")
        @NotNull(message = "Code must be present")
        String code,

        @Schema(description = "Новый пароль", example = "newSecurePassword")
        @NotNull
        @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
        String newPassword
) {}
