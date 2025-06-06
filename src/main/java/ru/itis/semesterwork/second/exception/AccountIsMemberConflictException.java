package ru.itis.semesterwork.second.exception;

import java.util.UUID;

public class AccountIsMemberConflictException extends ConflictServiceException {
    public AccountIsMemberConflictException(String username) {
        super("Account with username %s already member of this project.".formatted(username));
    }
}
