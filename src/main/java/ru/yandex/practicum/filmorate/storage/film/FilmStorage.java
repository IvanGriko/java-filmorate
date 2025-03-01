package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film createFilm(Film film);

    Film getFilm(long filmId);

    Film updateFilm(Film film);

    Set<Long> getLikes(Long filmId);

    Film addLike(Long filmId, Long userId);

    Set<Long> removeLike(Long filmId, Long userId);

    Set<Film> getPopularFilms(Integer count);
}
