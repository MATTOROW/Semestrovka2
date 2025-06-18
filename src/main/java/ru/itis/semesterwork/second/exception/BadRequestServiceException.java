package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;

public class BadRequestServiceException extends ServiceException {
    public BadRequestServiceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
