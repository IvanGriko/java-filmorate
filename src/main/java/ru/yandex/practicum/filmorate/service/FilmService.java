package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage;

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film getFilm(long filmId) throws NotFoundException {
        return filmStorage.getFilm(filmId);
    }

    public Film updateFilm(Film film) throws NotFoundException {
        return filmStorage.updateFilm(film);
    }

    public Set<Long> getLikes(long filmId) throws NotFoundException {
        return filmStorage.getLikes(filmId);
    }

    public Film addLike(long filmId, long userId) throws NotFoundException {
        return filmStorage.addLike(filmId, userId);
    }

    public Film removeLike(long filmId, long userId) throws NotFoundException {
        return filmStorage.removeLike(filmId, userId);
    }

    public Set<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }
}
