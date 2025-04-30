package ru.itis.semesterwork.second.exception;

public class CustomAccountNotFoundException extends ServiceNotFoundException {
    public CustomAccountNotFoundException(String username) {
        super("Аккаунт с именем пользователя %s не найден.".formatted(username));
    }
}
