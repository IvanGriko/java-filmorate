package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.error("{} - {}", HttpStatus.BAD_REQUEST, e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String defaultMessage = allErrors.stream()
                .map(error -> Objects.requireNonNull(error.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        log.error("{} - {}", HttpStatus.BAD_REQUEST, defaultMessage);
        return new ErrorResponse(defaultMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error("{} - {}", HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRunTimeException(final RuntimeException e) {
        String errorMessage = "Произлшла внутренняя ошибка сервера: ";
        errorMessage += "Тип исключения - " + e.getClass().getSimpleName() + ". ";
        if (e.getMessage() != null && !e.getMessage().isEmpty()) {
            errorMessage += "Сообщение: " + e.getMessage();
        } else {
            errorMessage += "Сообщение отсутствует.";
        }
        log.error("{} - {}", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ErrorResponse(errorMessage);
    }

    @Getter
    public static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }

}
