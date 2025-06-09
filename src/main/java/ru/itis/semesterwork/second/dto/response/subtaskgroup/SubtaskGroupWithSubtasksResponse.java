package ru.itis.semesterwork.second.dto.response.subtaskgroup;

import ru.itis.semesterwork.second.dto.response.subtask.SubtaskResponse;

import java.util.List;
import java.util.UUID;

public record SubtaskGroupWithSubtasksResponse(
        UUID id,
        String name,
        Boolean completed,
        Integer position,
        List<SubtaskResponse> subtasks
) {}
