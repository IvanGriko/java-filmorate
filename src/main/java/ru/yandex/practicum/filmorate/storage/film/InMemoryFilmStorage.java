package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Repository
public class InMemoryFilmStorage implements FilmStorage {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private static final LocalDate FIRST_FILM_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();
    private long lastFilmId = 0L;
    Set<Genre> genresCollection = Set.of(
            new Genre(1, "Комедия"),
            new Genre(2, "Драма"),
            new Genre(3, "Мультфильм"),
            new Genre(4, "Триллер"),
            new Genre(5, "Документальный"),
            new Genre(6, "Боевик")
    );
    Set<Genre> genres = new HashSet<>(genresCollection);
    Set<Mpa> mpaCollection = Set.of(
            new Mpa(1, "G"),
            new Mpa(2, "PG"),
            new Mpa(3, "PG-13"),
            new Mpa(4, "R"),
            new Mpa(5, "NC-17")
    );
    Set<Mpa> mpaRatings = new HashSet<>(mpaCollection);

    @Override
    public Collection<Film> getFilms() {
        log.debug("Starting get films collection");
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        log.debug("Starting post {}", film);
        ++lastFilmId;
        film.setId(lastFilmId);
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
        log.debug("Film {} was posted", film.getName());
        return film;
    }

    @Override
    public Film getFilm(long filmId) throws NotFoundException {
        log.debug("Starting get film by ID {}", filmId);
        if (!films.containsKey(filmId)) {
            log.error("Film with ID {} is not found", filmId);
            throw new NotFoundException("Film with ID " + filmId + " is not found");
        }
        return films.get(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        log.debug("Starting update {}", film);
        if (!films.containsKey(film.getId())) {
            log.error("Film with ID {} is not found", film.getId());
            throw new NotFoundException("Фильм с ID " + film.getId() + " не найден");
        }
        Film updatedFilm = Film.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .duration(film.getDuration())
                .releaseDate(film.getReleaseDate())
                .likes(film.getLikes())
                .build();
        films.replace(film.getId(), updatedFilm);
        log.debug("Film {} is updated", updatedFilm.getName());
        return updatedFilm;
    }

    @Override
    public Set<Long> getLikes(long filmId) throws NotFoundException {
        log.debug("Starting get likes of film by ID {}", filmId);
        if (!films.containsKey(filmId)) {
            log.error("Film with ID {} is not found", filmId);
            throw new NotFoundException("Film with ID " + filmId + " is not found");
        }
        return films.get(filmId).getLikes();
    }

    @Override
    public Film addLike(long filmId, long userId) {
        log.debug("Starting add like to film by ID {}", filmId);
        if (!films.containsKey(filmId)) {
            log.error("Film with ID {} is not found", filmId);
            throw new NotFoundException("Film with ID " + filmId + " is not found");
        }
        films.get(filmId).getLikes().add(userId);
        log.debug("Like is added to film {}",  films.get(filmId).getName());
        return films.get(filmId);
    }

    @Override
    public Film removeLike(long filmId, long userId) throws NotFoundException {
        log.debug("Starting remove like of film by ID {}", filmId);
        if (!films.containsKey(filmId)) {
            log.error("Film with ID {} is not found", filmId);
            throw new NotFoundException("Film with ID " + filmId + " is not found");
        }
        films.get(filmId).getLikes().remove(userId);
        log.debug("Like is removed from film {}",  films.get(filmId).getName());
        return films.get(filmId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        log.debug("Starting get {} popular films", count);
        if (count < 1) {
            count = 10;
        }
        if (count > getFilms().size()) {
            count = getFilms().size();
        }
        return new ArrayList<>(getFilms()).stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
