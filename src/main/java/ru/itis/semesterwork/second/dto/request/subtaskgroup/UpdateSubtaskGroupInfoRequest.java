package ru.itis.semesterwork.second.dto.request.subtaskgroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateSubtaskGroupInfoRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 255, message = "Name must be at most 255 characters")
        String name
) {}
