package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @GetMapping ("/{filmId}")
    public Film getFilm(@PathVariable long filmId) {
        return filmService.getFilm(filmId);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{filmId}/likes")
    public Set<Long> getLikes(@PathVariable long filmId) {
        return filmService.getLikes(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable long filmId, @PathVariable long userId) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable long filmId, @PathVariable long userId) {
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular?count={count}")
    public Set<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        return filmService.getPopularFilms(count);
    }
}
