package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenServiceException extends ServiceException {
    public ForbiddenServiceException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
