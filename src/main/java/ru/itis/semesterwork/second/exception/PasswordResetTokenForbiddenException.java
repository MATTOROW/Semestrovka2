package ru.itis.semesterwork.second.exception;

public class PasswordResetTokenForbiddenException extends ForbiddenServiceException {
    public PasswordResetTokenForbiddenException() {
        super("Reset token is not valid! Check correctness or try to get new token!");
    }
}
