package ru.itis.semesterwork.second.dto.request.subtaskgroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Schema(description = "Запрос на обновление порядка групп подзадач")
public record UpdateSubtaskGroupOrderRequest(
        @Schema(description = "Список ID групп подзадач в новом порядке", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "List of group IDs must not be empty")
        List<@NotNull(message = "Group ID must not be null") UUID> orderedGroupIds
) {}
