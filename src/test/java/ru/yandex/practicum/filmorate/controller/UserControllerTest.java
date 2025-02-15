package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserControllerTest {

    private Validator validator;
    User testUser;

    @BeforeEach
    void start() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        testUser = User.builder()
                .id(2L)
                .email("user@yandex.ru")
                .login("login")
                .name("User")
                .birthday(LocalDate.of(1987, 5, 5))
                .build();
    }

    @Test
    void createWithEmptyEmailTest() {
        testUser.setEmail("");
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWithInvalidEmailTest() {
        testUser.setEmail("InvalidEmail@");
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWithEmptyLoginTest() {
        testUser.setLogin("");
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWithEmptyNameTest() {
        testUser = User.builder()
                .id(2L)
                .email("user@yandex.ru")
                .login("login")
                .birthday(LocalDate.of(1987, 5, 5))
                .build();
        Assertions.assertEquals("login", testUser.getName());
    }

    @Test
    void createWithInvalidBirthdayTest() {
        testUser.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);
        assertFalse(violations.isEmpty());
    }
}