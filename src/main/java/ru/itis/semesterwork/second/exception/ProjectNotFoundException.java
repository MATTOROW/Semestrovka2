package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ProjectNotFoundException extends ServiceNotFoundException {
    public ProjectNotFoundException(UUID innerId) {
        super("Project with id %s not found.".formatted(innerId));
    }
}
