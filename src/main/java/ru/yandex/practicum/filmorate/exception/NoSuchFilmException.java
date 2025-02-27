package ru.yandex.practicum.filmorate.exception;

public class NoSuchFilmException extends RuntimeException {
    public NoSuchFilmException(String s) {
        super(s);
    }
}