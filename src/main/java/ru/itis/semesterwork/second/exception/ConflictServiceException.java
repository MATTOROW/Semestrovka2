package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;

public class ConflictServiceException extends ServiceException {
    public ConflictServiceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
