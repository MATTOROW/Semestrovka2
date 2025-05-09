package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;

public class IconServiceException extends ServiceException {
    public IconServiceException() {
        super("Some error occur, please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public IconServiceException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
