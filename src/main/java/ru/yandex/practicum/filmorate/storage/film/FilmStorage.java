package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film createFilm(Film film);

    Film getFilm(long filmId) throws NotFoundException;

    Film updateFilm(Film film) throws NotFoundException;

    Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException;

    List<Genre> getGenres(long filmId);

    Set<Long> getLikes(long filmId) throws NotFoundException;

    Film addLike(long filmId, long userId) throws NotFoundException;

    Film removeLike(long filmId, long userId) throws NotFoundException;

    List<Film> getPopularFilms(Integer count);
}
