package ru.itis.semesterwork.second.dto.request.task;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateTaskCategoryRequest(

        @NotNull(message = "Category ID is required")
        UUID categoryId
) {}
