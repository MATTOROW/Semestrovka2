package ru.itis.semesterwork.second.dto.request.subtask;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на обновление подзадачи")
public record UpdateSubtaskInfoRequest(
        @Schema(description = "Новое название подзадачи", example = "Обновлённая подзадача", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name must not be blank")
        @Size(max = 150, message = "Name must be at most 150 characters")
        String name
) {}
