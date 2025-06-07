package ru.itis.semesterwork.second.dto.request.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.itis.semesterwork.second.validation.model.NullableField;

public record AccountUpdateRequest(

        NullableField<
                @NotNull
                @Pattern(
                        regexp = "^[\\p{L}0-9_-]{3,50}$",
                        message = "Username must be between 3 and 50 characters..."
                )
                String
                > username,


        NullableField<
                @Size(min = 0, max = 1000, message = "Description length cannot be over 1000 letters")
                String
                > description
) {}
