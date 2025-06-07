package ru.itis.semesterwork.second.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

public record ProjectResponse(
        @Schema(description = "UUID проекта")
        UUID id,

        @Schema(description = "Название проекта")
        String name,

        @Schema(description = "Описание проекта")
        String description,

        @Schema(description = "Дата создания")
        Instant createdAt,

        @Schema(description = "Роль текущего пользователя в проекте")
        String currentUserRole
) {}
