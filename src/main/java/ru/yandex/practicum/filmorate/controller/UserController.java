package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDbStorage userDbStorage;

    @GetMapping
    public Collection<User> getUsers() {
        return userDbStorage.getUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userDbStorage.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userDbStorage.getUser(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        return userDbStorage.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Long addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userDbStorage.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable long id) {
        return userDbStorage.getFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable long id, @PathVariable long friendId) {
        return userDbStorage.removeFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends")
    public User removeAllFriends(@PathVariable long id) {
        return userDbStorage.removeAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @GetMapping("/{id}/friends/{otherId}")
    public boolean friendshipVerification(@PathVariable long id, @PathVariable long otherId) {
        return userService.friendshipVerification(id, otherId);
    }
}