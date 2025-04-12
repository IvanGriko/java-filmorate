package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Genre getGenreById(Integer genreId) {
        return genreRepository.getGenreById(genreId);
    }

    public Set<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    public Set<Genre> getGenresByFilm(Film film) {
        return genreRepository.getGenresByFilm(film);
    }

    public void updateGenreFilm(Film film) {
        genreRepository.updateGenreFilm(film);
    }

}