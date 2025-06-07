package ru.itis.semesterwork.second.dto.response.task;

import ru.itis.semesterwork.second.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskShortResponse(
        UUID id,
        String name,
        TaskStatus status,
        LocalDateTime deadline,
        Integer position
) {}
