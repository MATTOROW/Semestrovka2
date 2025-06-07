package ru.itis.semesterwork.second.dto.request.subtaskgroup;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateSubtaskGroupOrderRequest(
        @NotEmpty(message = "List of group IDs must not be empty")
        List<@NotNull(message = "Group ID must not be null") UUID> orderedGroupIds
) {}
