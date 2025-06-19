package ru.itis.semesterwork.second.exception;

public class ProjectMemberNotFoundException extends NotFoundServiceException {
    public ProjectMemberNotFoundException(String username) {
        super("Member with id %s not found.".formatted(username));
    }
}
