package ru.itis.semesterwork.second.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Schema(description = "Запрос на создание задачи")
public record CreateTaskRequest(
        @Schema(description = "Название задачи", example = "Рефакторинг кода", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Task name is required")
        @Size(max = 70, message = "Task name must be at most 70 characters")
        String name,

        @Schema(description = "Описание задачи", example = "Необходимо провести рефакторинг модуля авторизации",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description,

        @Schema(description = "Дедлайн задачи", example = "2023-12-31T23:59:59Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Instant deadline
) {}
