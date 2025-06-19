package ru.itis.semesterwork.second.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.semesterwork.second.exception.ServiceException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    @ExceptionHandler(ServiceException.class)
    public Object handleServiceException(ServiceException ex, WebRequest request, HttpServletRequest httpRequest) {
        HttpStatus status = ex.getStatus();

        if (isHtmlRequest(request)) {
            ModelAndView model = new ModelAndView("error");
            model.addObject("status", status.value());
            model.addObject("error", status.getReasonPhrase());
            model.addObject("message", ex.getMessage());
            return model;
        }

        return ResponseEntity
                .status(ex.getStatus())
                .body(createJsonResponse(request, httpRequest, ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request,
            HttpServletRequest httpRequest
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = getSimpleFieldName(violation.getPropertyPath().toString());
            errors.put(fieldName, violation.getMessage());
        });

        Map<String, Object> errorAttr = createJsonResponse(
                request,
                httpRequest,
                HttpStatus.BAD_REQUEST,
                "Validation error."
        );
        errorAttr.put("fields", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorAttr);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(
            HttpMessageNotReadableException ex,
            WebRequest request,
            HttpServletRequest httpRequest) {

        ex.getMostSpecificCause();
        String message = ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createJsonResponse(request, httpRequest, HttpStatus.BAD_REQUEST, message));
    }

    private String getSimpleFieldName(String fullFieldPath) {
        return fullFieldPath.substring(fullFieldPath.lastIndexOf('.') + 1);
    }

    private boolean isHtmlRequest(WebRequest request) {
        String acceptHeader = request.getHeader("Accept");
        return acceptHeader != null && acceptHeader.contains("text/html");
    }

    private Map<String, Object> createJsonResponse(
            WebRequest request,
            HttpServletRequest httpRequest,
            HttpStatus status,
            String message
    ) {
        Map<String, Object> errorAttr = this.errorAttributes.getErrorAttributes(
                request,
                ErrorAttributeOptions.defaults()
        );
        errorAttr.put("status", status.value());
        errorAttr.put("error", status.getReasonPhrase());
        errorAttr.put("message", message);
        errorAttr.put("path", httpRequest.getRequestURI());
        return errorAttr;
    }
}
