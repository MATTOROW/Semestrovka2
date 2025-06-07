package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class SubtaskGroupNotFoundException extends NotFoundServiceException {
    public SubtaskGroupNotFoundException(UUID id) {
        super("Subtask group with id %s not found".formatted(id));
    }
}
