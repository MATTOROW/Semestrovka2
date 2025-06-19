package ru.itis.semesterwork.second.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import ru.itis.semesterwork.second.validation.model.NullableField;

import java.time.Instant;

@Schema(description = "Запрос на обновление задачи")
public record UpdateTaskInfoRequest(
        @Schema(description = "Новое название задачи", example = "Обновлённая задача", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        NullableField<
                @Size(max = 70, message = "Task name must be at most 70 characters")
                        String
                > name,

        @Schema(description = "Новое описание задачи", example = "Новое описание задачи", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        NullableField<
                @Size(max = 1000, message = "Description must be at most 1000 characters")
                        String
                > description,

        @Schema(description = "Новый дедлайн задачи", example = "2023-12-31T23:59:59Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        NullableField<Instant> deadline
) {}
