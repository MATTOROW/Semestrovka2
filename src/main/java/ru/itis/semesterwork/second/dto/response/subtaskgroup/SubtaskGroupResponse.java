package ru.itis.semesterwork.second.dto.response.subtaskgroup;

import java.util.UUID;

public record SubtaskGroupResponse (
    UUID id,
    String name,
    Boolean completed,
    Integer position
) {}
