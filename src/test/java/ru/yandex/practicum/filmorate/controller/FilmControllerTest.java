package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class FilmControllerTest {
    Film testFilm;
    private Validator validator;

    @BeforeEach
    void start() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
        testFilm = Film.builder()
                .id(1L)
                .name("film")
                .description("testFilm")
                .releaseDate(LocalDate.of(2025,1, 1))
                .duration(120)
                .build();
    }

    @Test
    void createWithEmptyNameTest() {
        testFilm.setName("");
        Set<ConstraintViolation<Film>> violations = validator.validate(testFilm);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWithDescriptionOver200Symbols() {
        String overloadedDescription = RandomStringUtils.randomAlphabetic(201);
        testFilm.setDescription(overloadedDescription);
        Set<ConstraintViolation<Film>> violations = validator.validate(testFilm);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWithInvalidReleaseDate() {
        testFilm.setReleaseDate(LocalDate.of(1895,12,27));
        Set<ConstraintViolation<Film>> violations = validator.validate(testFilm);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void createWithNegativeDurationTest() {
        testFilm.setDuration(-1);
        Set<ConstraintViolation<Film>> violations = validator.validate(testFilm);
        assertFalse(violations.isEmpty());
    }
}