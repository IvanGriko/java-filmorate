package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class GenreRepository {

    private final JdbcTemplate jdbcTemplate;

    public Genre getGenreById(Integer genreId) {
        if (genreId < 1 || !getAllGenres().contains(new Genre(genreId, ""))) {
            throw new NotFoundException("genreId is not found");
        }
        String sql = "SELECT * FROM genres WHERE genre_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), genreId)
                .stream().findAny().orElse(null);
    }

    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genres ORDER BY genre_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    public Set<Genre> getGenresByFilm(Film film) {
        String sql = "SELECT g.genre_id, g.name FROM genres g NATURAL JOIN film_genres fg WHERE fg.film_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), film.getId()));
    }

    public void updateGenreFilm(Film film) {
        String sql = "DELETE FROM film_genres WHERE film_id = ?";
        jdbcTemplate.update(sql, film.getId());
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("name"));
    }

//    public List<Genre> getAllGenres() {
//        String sql = "SELECT * FROM genres ORDER BY genre_id";
//        return jdbcTemplate.query(sql, this::mapRowToGenre);
//    }
//
//    public Optional<Genre> getGenreById(int genreId) {
//        String sql = "SELECT * FROM genres WHERE genre_id = ?";
//        List<Genre> result = jdbcTemplate.query(sql, this::mapRowToGenre, genreId);
//        return result.stream().findFirst();
//    }
//
//    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
//        Genre genre = new Genre();
//        genre.setGenreId(rs.getInt("genre_id"));
//        genre.setName(rs.getString("name"));
//        return genre;
//    }
}
