package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class SubtaskNotFoundException extends ServiceNotFoundException {
    public SubtaskNotFoundException(UUID innerId) {
        super("Subtask with id %s not found.".formatted(innerId));
    }
}
