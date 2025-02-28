package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Film getFilm(long filmId) {
        return filmStorage.getFilm(filmId);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Set<Long> getLikes(Long filmId) {
        return filmStorage.getLikes(filmId);
    }

    public Set<Long> addLike(Long filmId, Long userId) {
        return filmStorage.addLike(filmId, userId);
    }

    public Set<Long> removeLike(Long filmId, Long userId) {
        return filmStorage.removeLike(filmId, userId);
    }

    public Set<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }
}
