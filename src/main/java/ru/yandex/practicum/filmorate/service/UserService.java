package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User getUser(long userId) {
        return userStorage.getUser(userId);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User addFriend(long userId, long friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public Set<Long> getFriends(long userId) {
        return userStorage.getFriends(userId);
    }

    public User removeFriend(long userId, long friendId) {
        return userStorage.removeFriend(userId, friendId);
    }

    public User removeAllFriends(long userId) {
        return userStorage.removeAllFriends(userId);
    }

    public Set<Long> getCommonFriends(long user1Id, long user2Id) {
        return userStorage.getCommonFriends(user1Id, user2Id);
    }
}
