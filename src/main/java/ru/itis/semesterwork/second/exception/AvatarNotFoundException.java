package ru.itis.semesterwork.second.exception;

public class AvatarNotFoundException extends NotFoundServiceException {
    public AvatarNotFoundException(String username) {
        super("Avatar for user with username %s not found.".formatted(username));
    }
}
