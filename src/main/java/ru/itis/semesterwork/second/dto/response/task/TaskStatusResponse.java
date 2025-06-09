package ru.itis.semesterwork.second.dto.response.task;

import ru.itis.semesterwork.second.model.TaskStatus;

public record TaskStatusResponse(
        TaskStatus newStatus
) {}
