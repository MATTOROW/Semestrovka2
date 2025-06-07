package ru.itis.semesterwork.second.dto.response.task;

import ru.itis.semesterwork.second.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskFullResponse(
        UUID id,
        String name,
        String description,
        TaskStatus status,
        LocalDateTime created,
        LocalDateTime deadline,
        String author
) {}
