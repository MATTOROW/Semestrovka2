package ru.itis.semesterwork.second.dto.request;

public record AccountRequest(String username, String email, String password, String description, String imageUrl) {
}
