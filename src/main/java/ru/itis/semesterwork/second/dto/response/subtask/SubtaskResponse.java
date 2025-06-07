package ru.itis.semesterwork.second.dto.response.subtask;

import java.util.UUID;

public record SubtaskResponse(
    UUID id,
    String name,
    Boolean completed,
    Integer position
) {}
