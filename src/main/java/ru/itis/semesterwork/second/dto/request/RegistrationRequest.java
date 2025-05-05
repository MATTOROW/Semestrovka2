package ru.itis.semesterwork.second.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record RegistrationRequest(
        @NotBlank(message = "Username is required")
        @Pattern(regexp = "^[\\p{L}0-9_-]{3,50}$",
                message = "Username must be between 3 and 50 characters and can contain letters, numbers, underscores, or hyphens")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
        String password,

        String description,
        MultipartFile icon
) {

        public boolean hasIcon() {
                return icon != null && !icon.isEmpty();
        }
}
