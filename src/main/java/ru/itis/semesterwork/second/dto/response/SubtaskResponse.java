package ru.itis.semesterwork.second.dto.response;

import java.util.UUID;

public record SubtaskResponse(UUID innerId, String name, String description, Boolean completed) {
}
