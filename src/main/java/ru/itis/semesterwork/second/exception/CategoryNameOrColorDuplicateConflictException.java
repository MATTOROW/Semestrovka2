package ru.itis.semesterwork.second.exception;

public class CategoryNameOrColorDuplicateConflictException extends ConflictServiceException {
    public CategoryNameOrColorDuplicateConflictException(String name, String color) {
        super("A category with name %s or color %s already exists in the project.".formatted(name, color));
    }
}
