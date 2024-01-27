package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
public class UserService {
    UserStorage storage;
    FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage storage, FriendStorage friendStorage) {
        this.storage = storage;
        this.friendStorage = friendStorage;
    }

    public void addFriend(Long firstId, Long secondId) {
        storage.getData(firstId);
        storage.getData(secondId);

        friendStorage.addFriend(firstId, secondId);
    }

    public void deleteFriend(Long firstId, Long secondId) {
        storage.getData(firstId);
        storage.getData(secondId);

        friendStorage.deleteFriend(firstId, secondId);
    }

    public List<User> getFriends(Long id) {
        storage.getData(id);

        return friendStorage.getFriends(id);
    }

    public List<User> getCommonFriends(Long firstId, Long secondId) {
        storage.getData(firstId);
        storage.getData(secondId);

        return friendStorage.getCommonFriends(firstId, secondId);
    }

    public User create(User data) {
        return storage.create(data);
    }

    public User update(User data) {
        return storage.update(data);
    }

    public List<User> getAll() {
        return storage.getAll();
    }

    public User getData(Long id) {
        return storage.getData(id);
    }
}
