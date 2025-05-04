package ru.itis.semesterwork.second.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Для получения дефолтных атрибутов JSON при ошибке
    private final ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    @ExceptionHandler(ServiceException.class)
    public Object handleServiceException(ServiceException ex, WebRequest request, HttpServletRequest httpRequest) {
        HttpStatus status = ex.getStatus();

        // Если запрос с Accept text/html, то возвращаем страничку, иначе JSON
        if (isHtmlRequest(request)) {
            ModelAndView model = new ModelAndView("error");
            model.addObject("status", status.value());
            model.addObject("error", status.getReasonPhrase());
            model.addObject("message", ex.getMessage());
            return model;
        }

        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(
                request,
                ErrorAttributeOptions.defaults()
        );

        errorAttributes.put("status", ex.getStatus().value());
        errorAttributes.put("error", ex.getStatus().getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", httpRequest.getRequestURI());

        return ResponseEntity
                .status(ex.getStatus())
                .body(errorAttributes);
    }

    private boolean isHtmlRequest(WebRequest request) {
        String acceptHeader = request.getHeader("Accept");
        return acceptHeader != null && acceptHeader.contains("text/html");
    }
}
