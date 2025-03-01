package ru.yandex.practicum.filmorate.exception;

import java.io.FileNotFoundException;

public class NotFoundException extends FileNotFoundException {
    public NotFoundException(String message) {
        super(message);
    }
}
