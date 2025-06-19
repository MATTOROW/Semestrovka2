package ru.itis.semesterwork.second.dto.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.itis.semesterwork.second.validation.model.NullableField;

@Schema(description = "Запрос на обновление аккаунта")
public record AccountUpdateRequest(
        @Schema(description = "Username аккаунта", example = "newUsername", nullable = true)
        NullableField<
                @NotNull
                @Pattern(
                        regexp = "^[\\p{L}0-9_-]{3,50}$",
                        message = "Username must be between 3 and 50 characters..."
                )
                        String
                > username,

        @Schema(description = "Описание аккаунта", example = "About me", nullable = true)
        NullableField<
                @Size(min = 0, max = 1000, message = "Description length cannot be over 1000 letters")
                        String
                > description
) {}
