package ru.itis.semesterwork.second.dto.request;

import java.time.LocalDateTime;

public record TaskRequest(String name, String description, LocalDateTime startDate, LocalDateTime endDate) {
}
