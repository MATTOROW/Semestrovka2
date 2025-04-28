package ru.itis.semesterwork.second.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponse(
        UUID innerId,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String status
) {
}
