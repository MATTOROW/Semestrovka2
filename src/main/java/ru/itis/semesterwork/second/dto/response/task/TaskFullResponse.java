package ru.itis.semesterwork.second.dto.response.task;

import ru.itis.semesterwork.second.model.TaskStatus;

import java.time.Instant;
import java.util.UUID;

public record TaskFullResponse(
        UUID id,
        String name,
        String description,
        TaskStatus status,
        Instant created,
        Instant deadline,
        String author
) {}
