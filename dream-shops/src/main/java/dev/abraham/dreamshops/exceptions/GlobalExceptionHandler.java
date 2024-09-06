package dev.abraham.dreamshops.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "Permission denied";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
