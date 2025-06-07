package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class TaskNotFoundException extends NotFoundServiceException {
    public TaskNotFoundException(UUID id) {
        super("Task with id %s not found.".formatted(id));
    }
}
