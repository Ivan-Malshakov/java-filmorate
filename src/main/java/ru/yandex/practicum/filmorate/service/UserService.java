package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    UserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(Long firstId, Long secondId) {
        User userFirst = getData(firstId);
        User userSecond = getData(secondId);
        userSecond.putFriend(userFirst);
        userFirst.putFriend(userSecond);
    }

    public void deleteFriend(Long firstId, Long secondId) {
        User userFirst = getData(firstId);
        User userSecond = getData(secondId);
        userSecond.removeFriend(userFirst);
        userFirst.removeFriend(userSecond);
    }

    public List<User> getFriends(Long id) {
        List<User> friends = new ArrayList<>();
        User user = storage.getData(id);
        for (Long idFriend : user.getFriends()) {
            if (storage.getData(idFriend) != null) {
                friends.add(storage.getData(idFriend));
            }
        }
        return friends;
    }

    public List<User> getCommonFriends(Long firstId, Long secondId) {
        List<User> commonFriends = new ArrayList<>();
        List<User> firstUserFriends = getFriends(firstId);
        List<User> secondUserFriends = getFriends(secondId);

        if ((firstUserFriends.size() != 0) && (secondUserFriends.size() != 0)) {
            for (User user : secondUserFriends) {
                if (firstUserFriends.contains(user)) {
                    commonFriends.add(user);
                }
            }
        }
        return commonFriends;
    }

    public User create(User data) {
        storage.create(data);
        return data;
    }

    public User update(User data) {
        storage.update(data);
        return data;
    }

    public List<User> getAll() {
        return storage.getAll();
    }

    public User getData(Long id) {
        return storage.getData(id);
    }

}
