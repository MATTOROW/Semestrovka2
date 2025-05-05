package ru.itis.semesterwork.second.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record AccountUpdateRequest(
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Email(message = "Invalid email format")
        String email,

        @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
        String password,

        String description,
        MultipartFile icon
) {
        public boolean hasIcon() {
                return icon != null && !icon.isEmpty();
        }
}
