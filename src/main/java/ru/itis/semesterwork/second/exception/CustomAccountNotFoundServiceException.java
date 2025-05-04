package ru.itis.semesterwork.second.exception;

public class CustomAccountNotFoundServiceException extends NotFoundServiceException {
    public CustomAccountNotFoundServiceException(String username) {
        super("Account with username %s not found.".formatted(username));
    }
}
