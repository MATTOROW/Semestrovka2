package ru.itis.semesterwork.second.dto.request.subtaskgroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSubtaskGroupRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 150, message = "Name must be at most 150 characters")
        String name
) {}
