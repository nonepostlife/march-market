package ru.geekbrains.march.market.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new AppError("USERNAME_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleRegistrationException(RegistrationException e) {
        return new ResponseEntity<>(new AppError("USER_REGISTRATION_ERROR", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
