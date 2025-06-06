package ru.itis.semesterwork.second.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

public record ProjectMemberResponse(
        @Schema(description = "UUID участника")
        String username,

        @Schema(description = "Роль")
        String role,

        @Schema(description = "Дата добавления")
        Instant joinedAt
) {}
