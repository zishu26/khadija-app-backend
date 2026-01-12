package khadija.app.backend.controller;

import khadija.app.backend.exception.DetailCannotBeUpdated;
import khadija.app.backend.exception.UserNotFoundException;
import khadija.app.backend.model.Validation;
import jakarta.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // User not found Exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Validation> handleUserNotFoundException(
            UserNotFoundException ex, HttpServletRequest request) {

        Validation validation =
                new Validation(
                        HttpStatus.NOT_FOUND.value(),
                        "User not found",
                        ex.getMessage(),
                        LocalDateTime.now(),
                        request.getRequestURI());
        return new ResponseEntity<>(validation, HttpStatus.NOT_FOUND);
    }

    // Cannot update primary registration details Exception
    @ExceptionHandler(DetailCannotBeUpdated.class)
    public ResponseEntity<Validation> handleDetailCannotBeUpdatedException(
            DetailCannotBeUpdated ex, HttpServletRequest request) {
        String message = ex.getMessage();
        String errorMessage = "Detail cannot be updated";
        if (!(message == null || message.trim().isEmpty())) {
            String fieldName = message.split(" ")[0];
            errorMessage = MessageFormat.format("{0} cannot be changed", fieldName);
        }
        Validation validation =
                new Validation(
                        HttpStatus.BAD_REQUEST.value(),
                        errorMessage,
                        ex.getMessage(),
                        LocalDateTime.now(),
                        request.getRequestURI());
        return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
    }

    // Parent Exception Handler, if any other exception is thrown
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Validation> handleException(Exception ex, HttpServletRequest request) {
        Validation validation =
                new Validation(
                        HttpStatus.BAD_REQUEST.value(),
                        "Internal Server Error",
                        ex.getMessage(),
                        LocalDateTime.now(),
                        request.getRequestURI());
        return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
    }
}
