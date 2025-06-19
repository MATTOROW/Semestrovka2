package ru.itis.semesterwork.second.dto.request.subtaskgroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на обновление группы подзадач")
public record UpdateSubtaskGroupInfoRequest(
        @Schema(description = "Новое название группы подзадач", example = "Обновлённая группа", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name must not be blank")
        @Size(max = 150, message = "Name must be at most 150 characters")
        String name
) {}
