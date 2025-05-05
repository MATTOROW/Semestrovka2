package ru.itis.semesterwork.second.exception;

public class UsernameOrEmailConflictException extends ConflictServiceException {
    public UsernameOrEmailConflictException(String username, String email) {
        super("User with email %s or username %s already exists.".formatted(email, username));
    }
}
