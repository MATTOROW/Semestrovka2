package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class TaskNotFoundServiceException extends NotFoundServiceException {
    public TaskNotFoundServiceException(UUID innerId) {
        super("Task with id %s not found.".formatted(innerId));
    }
}
