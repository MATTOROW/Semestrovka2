package ru.itis.semesterwork.second.dto.response;

import java.util.UUID;

public record ProjectResponse(UUID innerId, String name, String description) {
}
