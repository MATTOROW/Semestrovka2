package ru.itis.semesterwork.second.dto.request.subtask;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание подзадачи")
public record CreateSubtaskRequest(
        @Schema(description = "Название подзадачи", example = "Проверить код", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name must not be blank")
        @Size(max = 150, message = "Name must be at most 150 characters")
        String name
) {}
