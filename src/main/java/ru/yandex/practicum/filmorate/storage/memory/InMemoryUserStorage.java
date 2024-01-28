package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Component
@Slf4j
public class InMemoryUserStorage extends InMemoryBaseStorage<User> implements UserStorage {
    public InMemoryUserStorage() {
    }

    @Override
    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("No user name: was set login '{}'.", user.getLogin());
        }
    }

    @Override
    public boolean isExists(Long id) {
        throw new UnsupportedOperationException();
    }

}
