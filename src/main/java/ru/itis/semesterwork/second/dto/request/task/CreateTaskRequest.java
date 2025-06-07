package ru.itis.semesterwork.second.dto.request.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTaskRequest(
        @NotBlank(message = "Task name is required")
        @Size(max = 255, message = "Task name must be at most 255 characters")
        String name,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description,

        LocalDateTime deadline
) {}
