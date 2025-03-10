package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

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

    public Set<Long> getLikes(long filmId) {
        return filmStorage.getLikes(filmId);
    }

    public Film addLike(long filmId, long userId) {
        User u = userStorage.getUser(userId);
        if (u == null) {
            throw new NotFoundException("User is not found");
        }
        return filmStorage.addLike(filmId, userId);
    }

    public Film removeLike(long filmId, long userId) {
        User u = userStorage.getUser(userId);
        if (u == null) {
            throw new NotFoundException("User is not found");
        }
        return filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }
}
