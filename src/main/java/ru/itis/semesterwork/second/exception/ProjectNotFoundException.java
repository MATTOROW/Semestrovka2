package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ProjectNotFoundException extends ServiceNotFoundException {
    public ProjectNotFoundException(UUID innerId) {
        super("Проект с id %s не найден.".formatted(innerId));
    }
}
