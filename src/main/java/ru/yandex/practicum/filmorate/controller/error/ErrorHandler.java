package ru.yandex.practicum.filmorate.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public int handleDataNotFoundException(final DataNotFoundException e) {
        return new ErrorResponse("Data error", e.getMessage(), 404).getStatus();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public int handleValidationException(final ValidationException e) {
        return new ErrorResponse("Validation error", e.getMessage(), 400).getStatus();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public int handleRuntimeException(final Exception e) {
        return new ErrorResponse("Error", e.getMessage(), 500).getStatus();
    }

}
