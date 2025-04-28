package ru.itis.semesterwork.second.dto.response;

import java.util.UUID;

public record AccountResponse(UUID innerId, String username, String password, String description, String iconPath) {
}
