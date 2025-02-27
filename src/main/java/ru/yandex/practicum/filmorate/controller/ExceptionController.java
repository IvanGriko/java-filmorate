package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;

import java.util.Map;

@RestController
public class ExceptionController {@ExceptionHandler({ValidationFilmException.class, ValidationFilmException.class})

@ResponseStatus(HttpStatus.BAD_REQUEST)
public Map<String, String> validationFilmExceptionHandler(Exception e) {
    return Map.of("Error: ", e.getMessage());
}

    @ExceptionHandler({NoSuchUserException.class, NoSuchFilmException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> objectNotFoundHandler(Exception e) {
        return Map.of("Error: ", e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> deserializationAndValidationExceptionHandler(Exception e) {
        return Map.of("Error: ", "Неправильно введены данные");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> someExceptionHandler(Exception e) {
        System.out.println(e.getClass());
        System.out.println(e.getMessage());
        for(StackTraceElement i : e.getStackTrace()) System.out.println(i);
        return Map.of("Error: ", e.getMessage());
    }
}
