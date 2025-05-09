package ru.itis.semesterwork.second.exception;

public class UsernameConflictException extends ConflictServiceException {
    public UsernameConflictException(String username) {
        super("User with username %s already exists.".formatted(username));
    }
}
