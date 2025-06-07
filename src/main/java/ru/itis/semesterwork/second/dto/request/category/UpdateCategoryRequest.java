package ru.itis.semesterwork.second.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(

        @NotBlank(message = "Название категории обязательно")
        @Size(min = 2, max = 50, message = "Название категории должно быть от 2 до 50 символов")
        @Schema(description = "Название категории", example = "В работе")
        String name,

        @NotBlank(message = "Цвет обязателен")
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Некорректный HEX-цвет")
        @Schema(description = "Цвет в HEX", example = "#FF0000")
        String color
) {}
