package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private final FilmService filmService;
    private final UserService userService;

    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
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
        User u = userService.getUser(userId);
        if (u == null) {
            throw new NotFoundException("User is not found");
        }
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable long filmId, @PathVariable long userId) {
        User u = userService.getUser(userId);
        if (u == null) {
            throw new NotFoundException("User is not found");
        }
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> getPopularFilms(@RequestParam @Positive Integer count) {
        if (count == null || count < 1) {
            count = 10;
        } else if (count > getFilms().size()) {
            count = getFilms().size();
        } else {
            count = 3;
        }
        return filmService.getPopularFilms(count);
    }
}
