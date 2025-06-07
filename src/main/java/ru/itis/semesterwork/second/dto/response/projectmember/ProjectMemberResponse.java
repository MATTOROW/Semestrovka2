package ru.itis.semesterwork.second.dto.response.projectmember;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record ProjectMemberResponse(
        @Schema(description = "Username участника")
        String username,

        @Schema(description = "Роль")
        String role,

        @Schema(description = "Дата добавления")
        Instant joinedAt
) {}
