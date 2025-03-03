package ru.yandex.practicum.filmorate.storage.user;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Repository
public class InMemoryUserStorage implements UserStorage {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();
    private Long lastUserId = 0L;

    @SneakyThrows
    @Override
    public Collection<User> getUsers() {
        if (!users.isEmpty()) {
            log.debug("Starting get users collection");
            return users.values();
        }
        log.error("users is empty");
        throw new NotFoundException("users map is empty");
    }

    @Override
    public User createUser(User user) {
        log.debug("Starting create user {}", user.getName());
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("User {} is created", user.getName());
        ++lastUserId;
        user.setId(lastUserId);
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
        return user;
    }

    @SneakyThrows
    @Override
    public User getUser(long userId) {
        if (users.containsKey(userId)) {
            log.debug("Starting get user by ID {}", userId);
            return users.get(userId);
        }
        log.error("User with ID {} is not found", userId);
        throw new NotFoundException("User with ID " + userId + "not found");
    }

    @SneakyThrows
    @Override
    public User updateUser(User user) {
        log.debug("Starting update {}", user);
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setLogin(user.getLogin());
        newUser.setEmail(user.getEmail());
        newUser.setBirthday(user.getBirthday());
        if (user.getName() == null || user.getName().isBlank()) {
            newUser.setName(user.getLogin());
        } else {
            newUser.setName(user.getName());
        }
        newUser.setFriends(new HashSet<>(user.getFriends()));
        users.replace(user.getId(), newUser);
        log.debug("User {} is updated", newUser.getName());
        return users.get(user.getId());
    }

    @SneakyThrows
    @Override
    public User addFriend(long userId, long friendId) {
        if (!users.containsKey(userId)) {
            log.error("User with ID {} is not found", userId);
            throw new NotFoundException("User with ID " + userId + "not found");
        }
        if (!users.containsKey(friendId)) {
            log.error("User with ID {} is not found", friendId);
            throw new NotFoundException("User with ID " + friendId + "not found");
        }
        log.debug("Starting add friend ID {} to user ID {}", friendId, userId);
        users.get(userId).getFriends().add(friendId);
        users.get(friendId).getFriends().add(userId);
        log.debug("Friend ID {} is added to user ID {}", friendId, userId);
        return users.get(userId);
    }

    @SneakyThrows
    @Override
    public Set<Long> getFriends(long userId) {
        if (users.containsKey(userId)) {
            log.debug("Starting get friends set of user with ID {}", userId);
            return users.get(userId).getFriends();
        }
        log.error("User with ID {} is not found", userId);
        throw new NotFoundException("User with ID " + userId + "not found");
    }

    @SneakyThrows
    @Override
    public User removeFriend(long userId, long friendId) {
        if (!users.containsKey(userId)) {
            log.error("User with ID {} is not found", userId);
            throw new NotFoundException("User with ID " + userId + "not found");
        }
        if (!users.containsKey(friendId)) {
            log.error("User with ID {} is not found", friendId);
            throw new NotFoundException("User with ID " + friendId + "not found");
        }
        log.debug("Starting remove friend ID {} from user ID {}", friendId, userId);
        users.get(userId).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(userId);
        log.debug("Friend ID {} is removed from user ID {}", friendId, userId);
        return users.get(userId);
    }

    @SneakyThrows
    @Override
    public User removeAllFriends(long userId) {
        if (!users.containsKey(userId)) {
            log.debug("Starting remove all friends of user with ID {}", userId);
            users.get(userId).getFriends().clear();
            log.debug("All friends of user with ID {} is removed", userId);
            return users.get(userId);
        }
        log.error("User with ID {} is not found", userId);
        throw new NotFoundException("User with ID " + userId + "not found");
    }

    @SneakyThrows
    @Override
    public Set<Long> getCommonFriends(long user1Id, long user2Id) {
        if (!users.containsKey(user1Id)) {
            log.error("User with ID {} is not found", user1Id);
            throw new NotFoundException("User with ID " + user1Id + "not found");
        }
        if (!users.containsKey(user2Id)) {
            log.error("User with ID {} is not found", user2Id);
            throw new NotFoundException("User with ID " + user2Id + "not found");
        }
        log.debug("Starting get common friends of users with ID {} and ID {}", user1Id, user2Id);
        Set<Long> friends1 = getFriends(user1Id);
        Set<Long> friends2 = getFriends(user2Id);
        return friends1.stream().filter(friends2::contains).collect(Collectors.toSet());
    }
}
