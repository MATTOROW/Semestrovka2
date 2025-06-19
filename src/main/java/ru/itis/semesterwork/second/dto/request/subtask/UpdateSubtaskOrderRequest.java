package ru.itis.semesterwork.second.dto.request.subtask;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Schema(description = "Запрос на обновление порядка подзадач")
public record UpdateSubtaskOrderRequest(
        @Schema(description = "Список ID подзадач в новом порядке", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "List of subtask IDs must not be empty")
        List<@NotNull(message = "Subtask ID must not be null") UUID> orderedSubtaskIds
) {}