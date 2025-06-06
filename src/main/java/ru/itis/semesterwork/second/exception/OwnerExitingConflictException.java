package ru.itis.semesterwork.second.exception;

public class OwnerExitingConflictException extends ConflictServiceException {
    public OwnerExitingConflictException() {
        super("Owner cannot leave the project. Transfer ownership first or delete project.");
    }
}
