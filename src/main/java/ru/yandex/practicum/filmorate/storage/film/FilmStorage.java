package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film createFilm(Film film);

    Film getFilmById(long filmId);

    Film getFilmByName(String name);

    Film updateFilm(Film film);

    Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException;

    Set<Long> getLikes(long filmId);

    Film addLike(long filmId, long userId);

    Film removeLike(long filmId, long userId);

    List<Film> getPopularFilms(Integer count);

}
