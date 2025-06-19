package ru.itis.semesterwork.second.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


@Schema(description = "Запрос на перемещение задачи в другую категорию")
public record UpdateTaskCategoryRequest(
        @Schema(description = "ID новой категории", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Category ID is required")
        UUID categoryId,

        @Schema(description = "Новая позиция задачи в категории", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        Integer position
) {}
