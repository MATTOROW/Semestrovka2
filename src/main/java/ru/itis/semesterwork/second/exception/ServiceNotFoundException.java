package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;

public class ServiceNotFoundException extends ServiceException {
    public ServiceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
