package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Genre getGenreById(Integer genreId) {
        return genreRepository.getGenreById(genreId);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    public Set<Genre> getGenresByFilm(Film film) {
        return genreRepository.getGenresByFilm(film);
    }

    public void updateGenreFilm(Film film) {
        genreRepository.updateGenreFilm(film);
    }

//    public List<Genre> getAllGenres() {
//        return genreRepository.getAllGenres();
//    }
//
//    public Genre getGenreById(int genreId) {
//        return genreRepository.getGenreById(genreId)
//                .orElseThrow(() -> new NotFoundException("Жанр с id=" + genreId + " не найден"));
//    }

}