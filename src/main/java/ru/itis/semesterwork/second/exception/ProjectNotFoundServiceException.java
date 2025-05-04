package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class ProjectNotFoundServiceException extends NotFoundServiceException {
    public ProjectNotFoundServiceException(UUID innerId) {
        super("Project with id %s not found.".formatted(innerId));
    }
}
