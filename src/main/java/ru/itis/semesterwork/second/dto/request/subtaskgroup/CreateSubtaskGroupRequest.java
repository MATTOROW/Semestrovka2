package ru.itis.semesterwork.second.dto.request.subtaskgroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание группы подзадач")
public record CreateSubtaskGroupRequest(
        @Schema(description = "Название группы подзадач", example = "Группа проверки", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name must not be blank")
        @Size(max = 150, message = "Name must be at most 150 characters")
        String name
) {}
