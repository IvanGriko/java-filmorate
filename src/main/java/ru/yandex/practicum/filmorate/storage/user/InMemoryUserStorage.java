package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();
    private Long lastUserId = 0L;

    @Override
    public Collection<User> getUsers() {
        log.debug("Starting get users collection");
        return users.values();
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
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(long userId) {
        log.debug("Starting get user by ID {}", userId);
        if (!users.containsKey(userId)) {
            log.error("User with ID {} is not found", userId);
            throw new NoSuchUserException("User with ID " + userId + "not found");
        }
        return users.get(userId);
    }

    @Override
    public User updateUser(User user) {
        log.debug("Starting update {}", user);
        if (!users.containsKey(user.getId())) {
            log.error("User with ID {} is not found", user.getId());
            throw new ValidationFilmException("Пользователь с ID " + user.getId() + " не найден");
        }
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

    @Override
    public Set<User> addFriend(long userId, long friendId) {
        log.debug("Starting add friend ID {} to user ID {}", friendId, userId);
        if (!users.containsKey(userId)) {
            log.error("User with ID {} is not found", userId);
            throw new NoSuchUserException("User with ID " + userId + "not found");
        }
        if (!users.containsKey(friendId)) {
            log.error("User with ID {} is not found", friendId);
            throw new NoSuchUserException("User with ID " + friendId + "not found");
        }
        User user = users.get(userId);
        User newFriend = users.get(friendId);
        user.getFriends().add(newFriend);
        newFriend.getFriends().add(user);
        users.replace(userId, user);
        log.debug("Friend ID {} is added to user ID {}", friendId, userId);
        return user.getFriends();
    }

    @Override
    public Set<User> getFriends(long userId) {
        log.debug("Starting get friends set of user with ID {}", userId);
        if (!users.containsKey(userId)) {
            log.error("User with ID {} is not found", userId);
            throw new NoSuchUserException("User with ID " + userId + "not found");
        }
        return users.get(userId).getFriends();
    }

    @Override
    public Set<User> removeFriend(long userId, long friendId) {
        log.debug("Starting remove friend ID {} from user ID {}", friendId, userId);
        if (!users.containsKey(userId)) {
            log.error("User with ID {} is not found", userId);
            throw new NoSuchUserException("User with ID " + userId + "not found");
        }
        if (!users.containsKey(friendId)) {
            log.error("User with ID {} is not found", friendId);
            throw new NoSuchUserException("User with ID " + friendId + "not found");
        }
        User user = users.get(userId);
        User friend = users.get(friendId);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        users.replace(userId, user);
        users.replace(friendId, friend);
        log.debug("Friend ID {} is removed from user ID {}", friendId, userId);
        return user.getFriends();
    }

    @Override
    public Set<User> removeAllFriends(long userId) {
        log.debug("Starting remove all friends of user with ID {}", userId);
        if (!users.containsKey(userId)) {
            log.error("User with ID {} is not found", userId);
            throw new NoSuchUserException("User with ID " + userId + "not found");
        }
        User user = users.get(userId);
        user.getFriends().clear();
        users.replace(userId, user);
        log.debug("All friends of user with ID {} is removed", userId);
        return user.getFriends();
    }

    @Override
    public Set<User> getCommonFriends(long user1Id, long user2Id) {
        log.debug("Starting get common friends of users with ID {} and ID {}", user1Id, user2Id);
        if (!users.containsKey(user1Id)) {
            log.error("User with ID {} is not found", user1Id);
            throw new NoSuchUserException("User with ID " + user1Id + "not found");
        }
        if (!users.containsKey(user2Id)) {
            log.error("User with ID {} is not found", user2Id);
            throw new NoSuchUserException("User with ID " + user2Id + "not found");
        }
        Set<User> friends1 = getFriends(user1Id);
        Set<User> friends2 = getFriends(user2Id);
        return friends1.stream().filter(friends2::contains).collect(Collectors.toSet());
    }
}
