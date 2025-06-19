package ru.itis.semesterwork.second.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание проекта")
public record CreateProjectRequest(
        @Schema(description = "Название проекта", example = "Мой проект", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Название проекта обязательно")
        @Size(min = 3, max = 100, message = "Название проекта должно быть от 3 до 100 символов")
        String name,

        @Schema(description = "Описание проекта", example = "Это тестовый проект", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description
) {}
