package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    Collection<User> getUsers();

    User createUser(User user);

    User getUserById(long userId);

    User getUserByName(String name);

    User updateUser(User user);

    Long addFriend(Long userId, Long friendId);

    Set<User> getFriends(Long userId);

    User removeFriend(long userId, long friendId);

    User removeAllFriends(long userId);

    Set<User> getCommonFriends(long user1Id, long user2Id);

    boolean friendshipVerification(long user1Id, long user2Id);

}
