package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @PatchMapping("/{id}/friends/{friendId}")
    public Set<User> addFriend(@PathVariable long id, @PathVariable long friendId) {
        return userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Set<User> removeFriend(@PathVariable long id, @PathVariable long friendId) {
        return userService.removeFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends")
    public Set<User> removeAllFriends(@PathVariable long id) {
        return userService.removeAllFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}