package ru.itis.semesterwork.second.exception;

public class CustomAccountNotFoundException extends ServiceNotFoundException {
    public CustomAccountNotFoundException(String username) {
        super("Account with username %s not found.".formatted(username));
    }
}
