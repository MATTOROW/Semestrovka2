package ru.itis.semesterwork.second.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на вход в систему")
public record LoginRequest(
        @Schema(description = "Логин или email", example = "user@example.com")
        String login,

        @Schema(description = "Пароль", example = "mySecurePassword")
        String password
) {}