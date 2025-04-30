package ru.itis.semesterwork.second.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(ServiceException ex, Model model) {
        HttpStatus status = ex.getStatus();
        model.addAttribute("errorCode", status.value());
        model.addAttribute("errorName", status.getReasonPhrase());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // имя HTML-шаблона (error.html)
    }
}
