package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film createFilm(Film film);

    Film getFilm(long filmId) throws NotFoundException;

    Film updateFilm(Film film) throws NotFoundException;

    Set<Long> getLikes(long filmId) throws NotFoundException;

    Film addLike(long filmId, long userId) throws NotFoundException;

    Film removeLike(long filmId, long userId) throws NotFoundException;

    Set<Film> getPopularFilms(int count);
}
