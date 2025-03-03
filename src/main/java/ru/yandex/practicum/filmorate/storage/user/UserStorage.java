package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    Collection<User> getUsers();

    User createUser(User user);

    User getUser(long userId);

    User updateUser(User user);

    User addFriend(long userId, long friendId);

    Set<Long> getFriends(long userId);

    User removeFriend(long userId, long friendId);

    User removeAllFriends(long userId);

    Set<Long> getCommonFriends(long user1Id, long user2Id);
}
