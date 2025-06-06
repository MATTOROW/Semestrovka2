package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class ProjectNotFoundException extends NotFoundServiceException {
    public ProjectNotFoundException(UUID uuid) {
        super("Project with id %s not found.".formatted(uuid));
    }
}
