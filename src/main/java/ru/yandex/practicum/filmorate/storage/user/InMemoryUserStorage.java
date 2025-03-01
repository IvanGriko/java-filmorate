package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(long userId) {
        if (users.containsKey(userId)) {
            log.debug("Starting get user by ID {}", userId);
            return users.get(userId);
        }
        log.error("User with ID {} is not found", userId);
        throw new NotFoundException("User with ID " + userId + "not found");
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            log.debug("Starting update {}", user);
            User updatedUser = User.builder()
                    .id(user.getId())
                    .login(user.getLogin())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .name(user.getName())
                    .build();
            users.replace(user.getId(), updatedUser);
            log.debug("User {} is updated", updatedUser.getName());
            return users.get(user.getId());
        }
//        log.error("User with ID {} is not found", user.getId());
        throw new NotFoundException("Пользователь с ID " + user.getId() + " не найден");
    }

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
        User user = users.get(userId);
        User newFriend = users.get(friendId);
        user.getFriends().add(newFriend);
        newFriend.getFriends().add(user);
        users.replace(userId, user);
        log.debug("Friend ID {} is added to user ID {}", friendId, userId);
        return users.get(userId);
    }

    @Override
    public Set<User> getFriends(long userId) {
        if (users.containsKey(userId)) {
            log.debug("Starting get friends set of user with ID {}", userId);
            return users.get(userId).getFriends();
        }
        log.error("User with ID {} is not found", userId);
        throw new NotFoundException("User with ID " + userId + "not found");
    }

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
        User user = users.get(userId);
        User friend = users.get(friendId);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        users.replace(userId, user);
        users.replace(friendId, friend);
        log.debug("Friend ID {} is removed from user ID {}", friendId, userId);
        return users.get(userId);
    }

    @Override
    public User removeAllFriends(long userId) {
        if (!users.containsKey(userId)) {
            log.debug("Starting remove all friends of user with ID {}", userId);
            User user = users.get(userId);
            user.getFriends().clear();
            users.replace(userId, user);
            log.debug("All friends of user with ID {} is removed", userId);
            return users.get(userId);
        }
        log.error("User with ID {} is not found", userId);
        throw new NotFoundException("User with ID " + userId + "not found");
    }

    @Override
    public Set<User> getCommonFriends(long user1Id, long user2Id) {
        if (!users.containsKey(user1Id)) {
            log.error("User with ID {} is not found", user1Id);
            throw new NotFoundException("User with ID " + user1Id + "not found");
        }
        if (!users.containsKey(user2Id)) {
            log.error("User with ID {} is not found", user2Id);
            throw new NotFoundException("User with ID " + user2Id + "not found");
        }
        log.debug("Starting get common friends of users with ID {} and ID {}", user1Id, user2Id);
        Set<User> friends1 = getFriends(user1Id);
        Set<User> friends2 = getFriends(user2Id);
        return friends1.stream().filter(friends2::contains).collect(Collectors.toSet());
    }
}
