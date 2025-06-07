package ru.itis.semesterwork.second.dto.request.subtask;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateSubtaskOrderRequest(
        @NotEmpty(message = "List of subtask IDs must not be empty")
        List<@NotNull(message = "Subtask ID must not be null") UUID> orderedSubtaskIds
) {}
