package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Long addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable long id, @PathVariable long friendId) {
        return userService.removeFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends")
    public User removeAllFriends(@PathVariable long id) {
        return userService.removeAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @GetMapping("/{id}/friends/{otherId}")
    public boolean friendshipVerification(long id, long otherId) {
        return userService.friendshipVerification(id, otherId);
    }

}