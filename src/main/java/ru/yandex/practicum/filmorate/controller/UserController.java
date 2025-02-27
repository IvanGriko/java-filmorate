package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

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

    @PatchMapping("/{userId}/friends/{friendId}")
    public Set<User> addFriend(@PathVariable long userId, @PathVariable long friendId) {
        return userService.addFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public Set<User> getFriends(@PathVariable long userId) {
        return userService.getFriends(userId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public Set<User> removeFriend(@PathVariable long userId, @PathVariable long friendId) {
        return userService.removeFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends")
    public Set<User> removeAllFriends(@PathVariable long userId) {
        return userService.removeAllFriends(userId);
    }

    @GetMapping("/users/{user1Id}/friends/common/{user2Id}")
    public Set<User> getCommonFriends(@PathVariable long user1Id, @PathVariable long user2Id) {
        return userService.getCommonFriends(user1Id, user2Id);
    }
}