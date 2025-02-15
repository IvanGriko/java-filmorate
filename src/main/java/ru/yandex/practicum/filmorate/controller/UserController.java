package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Data
@Builder
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Starting post {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("User {} is posted", user.getName());
        user.setId(generateUserId());
        users.put(user.getId(), user);
        return user;
    }

    private long generateUserId() {
        long maxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++maxId;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Starting update {}", user);
        if (users.containsKey(user.getId())) {
            User updatedUser = User.builder()
                    .id(user.getId())
                    .login(user.getLogin())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .name(user.getName())
                    .build();
            users.replace(user.getId(), updatedUser);
            log.debug("User {} is updated", updatedUser.getName());
            return updatedUser;
        }
        log.error("User with ID {} is not found", user.getId());
        throw new ValidationException("Пользователь с ID " + user.getId() + " не найден");
    }
}