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

    Film getFilm(long filmId);

    Film updateFilm(Film film);

    Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException;

    Set<Long> getLikes(long filmId);

    Film addLike(long filmId, long userId);

    Film removeLike(long filmId, long userId);

    List<Film> getPopularFilms(Integer count);

}
