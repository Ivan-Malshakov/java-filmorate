package ru.yandex.practicum.filmorate.storage.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class InMemoryUserStorageTest {
    InMemoryUserStorage storage;


    private static final String EMAIL = "user@yandex.ru";
    private static final String LOGIN = "UserLogin";
    private static final LocalDate BIRTHDAY = LocalDate.of(2000, 1, 1);

    @BeforeEach
    void setUp() {
        storage = new InMemoryUserStorage();
    }

    @Test
    void validateShouldSetLoginInNameWithEmptyName() {
        User user = User.builder()
                .email(EMAIL)
                .login(LOGIN)
                .name(" ")
                .birthday(BIRTHDAY)
                .build();

        storage.validate(user);

        final String actualUserName = "UserLogin";
        final String userName = user.getName();

        Assertions.assertEquals(actualUserName, userName, "Name doesn't match.");
    }

    @Test
    void validateShouldSetLoginInNameWithoutName() {
        User user = User.builder()
                .email(EMAIL)
                .login(LOGIN)
                .birthday(BIRTHDAY)
                .build();

        storage.validate(user);

        final String actualUserName = "UserLogin";
        final String userName = user.getName();

        Assertions.assertEquals(actualUserName, userName, "Name doesn't match.");
    }

    @Test
    void validateWithNameShouldBeOk() {
        User user = User.builder()
                .email(EMAIL)
                .login(LOGIN)
                .name("UserName")
                .birthday(BIRTHDAY)
                .build();

        storage.validate(user);

        final String actualUserName = "UserName";
        final String userName = user.getName();

        Assertions.assertEquals(actualUserName, userName, "Name doesn't match.");
    }
}
