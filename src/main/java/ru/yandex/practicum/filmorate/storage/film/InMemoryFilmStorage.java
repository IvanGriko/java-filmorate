package ru.yandex.practicum.filmorate.storage.film;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmsByLikesComparator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private static final LocalDate FIRST_FILM_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();
    private long lastFilmId = 0L;

    @Override
    public Collection<Film> getFilms() {
        log.debug("Starting get films collection");
        return films.values();
    }

    @Override
    public Film createFilm(@Valid @RequestBody Film film) {
        log.debug("Starting post {}", film);
        ++lastFilmId;
        film.setId(lastFilmId);
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
    public Film updateFilm(@Valid @RequestBody Film film) throws NotFoundException {
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

    public Set<Long> getLikes(long filmId) throws NotFoundException {
        log.debug("Starting get likes of film by ID {}", filmId);
        if (!films.containsKey(filmId)) {
            log.error("Film with ID {} is not found", filmId);
            throw new NotFoundException("Film with ID " + filmId + " is not found");
        }
        return films.get(filmId).getLikes();
    }

    public Film addLike(long filmId, long userId) throws NotFoundException {
        log.debug("Starting add like to film by ID {}", filmId);
        if (!films.containsKey(filmId)) {
            log.error("Film with ID {} is not found", filmId);
            throw new NotFoundException("Film with ID " + filmId + " is not found");
        }
        films.get(filmId).getLikes().add(userId);
        log.debug("Like is added to film {}",  films.get(filmId).getName());
        return films.get(filmId);
    }

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
    public Set<Film> getPopularFilms(Integer count) {
        log.debug("Starting get {} popular films", count);
        if (count == null || count < 1) count = 10;
        return getFilms().stream()
                .sorted(new FilmsByLikesComparator())
                .limit(count)
                .collect(Collectors.toSet());
    }
}
