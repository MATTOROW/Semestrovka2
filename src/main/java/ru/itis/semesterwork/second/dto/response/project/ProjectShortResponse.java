package ru.itis.semesterwork.second.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

public record ProjectShortResponse(
        @Schema(description = "UUID проекта")
        UUID id,

        @Schema(description = "Название проекта")
        String name,

        @Schema(description = "Описание проекта")
        String description,

        @Schema(description = "Дата создания")
        Instant createdAt
) {}
