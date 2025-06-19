package ru.itis.semesterwork.second.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание категории")
public record CreateCategoryRequest(
        @Schema(description = "Название категории", example = "В работе", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Название категории обязательно")
        @Size(min = 2, max = 30, message = "Название категории должно быть от 2 до 30 символов")
        String name,

        @Schema(description = "Цвет в HEX-формате", example = "#FF0000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Цвет обязателен")
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Некорректный HEX-цвет")
        String color
) {}
