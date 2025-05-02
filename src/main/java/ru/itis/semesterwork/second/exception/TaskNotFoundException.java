package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class TaskNotFoundException extends ServiceNotFoundException {
    public TaskNotFoundException(UUID innerId) {
        super("Task with id %s not found.".formatted(innerId));
    }
}
