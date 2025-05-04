package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class SubtaskNotFoundServiceException extends NotFoundServiceException {
    public SubtaskNotFoundServiceException(UUID innerId) {
        super("Subtask with id %s not found.".formatted(innerId));
    }
}
