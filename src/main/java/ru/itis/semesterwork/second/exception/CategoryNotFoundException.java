package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class CategoryNotFoundException extends NotFoundServiceException {
    public CategoryNotFoundException(UUID uuid) {
        super("Category with id %s not found.".formatted(uuid));
    }
}
