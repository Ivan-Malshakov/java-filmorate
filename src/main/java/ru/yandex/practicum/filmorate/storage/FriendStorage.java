package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {

    void addFriend(Long firstId, Long secondId);

    void deleteFriend(Long firstId, Long secondId);

    List<User> getFriends(Long userId);

    List<User> getCommonFriends(Long firstId, Long secondId);
}
