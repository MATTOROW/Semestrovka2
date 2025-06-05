package ru.itis.semesterwork.second.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record AccountUpdateRequest(
        @Pattern(regexp = "^[\\p{L}0-9_-]{3,50}$",
                message = "Username must be between 3 and 50 characters and can contain letters, numbers, underscores, or hyphens")
        Optional<String> username,

        @Size(min = 0, max = 1000, message = "Description length cannot be over 1000 letters")
        Optional<String> description
) {}
