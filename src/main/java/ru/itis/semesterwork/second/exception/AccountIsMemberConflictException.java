package ru.itis.semesterwork.second.exception;

public class AccountIsMemberConflictException extends ConflictServiceException {
    public AccountIsMemberConflictException(String username) {
        super("Account with username %s already member of this project.".formatted(username));
    }
}
