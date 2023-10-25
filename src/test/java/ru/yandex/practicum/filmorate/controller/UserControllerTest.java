package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {
    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController();
    }

    @Test
    void validateShouldSetLoginInNameWithEmptyName() {
        User user = User.builder()
                .email("user@yandex.ru")
                .login("UserLogin")
                .name(" ")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        controller.validate(user);

        final String actualUserName = "UserLogin";
        final String userName = user.getName();

        Assertions.assertEquals(actualUserName, userName, "Name doesn't match.");
    }

    @Test
    void validateShouldSetLoginInNameWithoutName() {
        User user = User.builder()
                .email("user@yandex.ru")
                .login("UserLogin")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        controller.validate(user);

        final String actualUserName = "UserLogin";
        final String userName = user.getName();

        Assertions.assertEquals(actualUserName, userName, "Name doesn't match.");
    }

    @Test
    void validateWithNameShouldBeOk() {
        User user = User.builder()
                .email("user@yandex.ru")
                .login("UserLogin")
                .name("UserName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        controller.validate(user);

        final String actualUserName = "UserName";
        final String userName = user.getName();

        Assertions.assertEquals(actualUserName, userName, "Name doesn't match.");
    }
}
