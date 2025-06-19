package ru.itis.semesterwork.second.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Запрос на удаление категории")
public record DeleteCategoryRequest(
        @Schema(description = "ID новой категории для переноса задач", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        UUID newCategoryId
) {}
