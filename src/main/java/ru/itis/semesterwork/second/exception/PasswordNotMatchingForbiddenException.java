package ru.itis.semesterwork.second.exception;

public class PasswordNotMatchingForbiddenException extends ForbiddenServiceException {
    public PasswordNotMatchingForbiddenException() {
        super("Incorrect password!");
    }
}
