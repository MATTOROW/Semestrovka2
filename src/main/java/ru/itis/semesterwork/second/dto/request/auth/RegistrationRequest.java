package ru.itis.semesterwork.second.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на регистрацию нового аккаунта")
public record RegistrationRequest(
        @Schema(description = "Username нового аккаунта", example = "newUser")
        @NotBlank(message = "Username is required")
        @Pattern(regexp = "^[\\p{L}0-9_-]{3,50}$",
                message = "Username must be between 3 and 50 characters and can contain letters, numbers, underscores, or hyphens")
        String username,

        @Schema(description = "Email нового аккаунта", example = "user@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "Пароль нового аккаунта", example = "securePassword123")
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
        String password
) {}
