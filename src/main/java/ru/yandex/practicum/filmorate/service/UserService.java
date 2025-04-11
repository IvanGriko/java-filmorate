package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.Collection;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserDbStorage userDbStorage;

    public Collection<User> getUsers() {
        return userDbStorage.getUsers();
    }

    public User createUser(User user) {
        return userDbStorage.createUser(user);
    }

    public User getUser(long userId) {
        User user = userDbStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User is not found");
        }
        return user;
    }

    public User updateUser(User user) {
        return userDbStorage.updateUser(user);
    }

    public Long addFriend(Long userId, Long friendId) {
        return userDbStorage.addFriend(userId, friendId);
    }

    public Set<User> getFriends(Long userId) {
        return userDbStorage.getFriends(userId);
    }

    public User removeFriend(long userId, long friendId) {
        return userDbStorage.removeFriend(userId, friendId);
    }

    public User removeAllFriends(long userId) {
        return userDbStorage.removeAllFriends(userId);
    }

    public Set<User> getCommonFriends(long user1Id, long user2Id) {
        return userDbStorage.getCommonFriends(user1Id, user2Id);
    }

    public boolean friendshipVerification(long user1Id, long user2Id) {
        return userDbStorage.friendshipVerification(user1Id, user2Id);
    }

}
