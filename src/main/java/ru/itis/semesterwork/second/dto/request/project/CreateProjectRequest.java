package ru.itis.semesterwork.second.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(

        @NotBlank(message = "Название проекта обязательно")
        @Size(min = 3, max = 100, message = "Название проекта должно быть от 3 до 100 символов")
        @Schema(description = "Название проекта", example = "Мой проект")
        String name,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        @Schema(description = "Описание проекта", example = "Это тестовый проект")
        String description
) {}
