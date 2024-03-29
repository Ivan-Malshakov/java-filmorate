package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage extends AbstractStorage<User> {
    boolean isExists(Long id);
}
