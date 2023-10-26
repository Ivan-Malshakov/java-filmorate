package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User> {

    @Override
    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("No user name: was set login '{}'.", user.getLogin());
        }
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Create user {}.", user);
        return super.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Update user {}.", user);
        return super.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Get all users.");
        return super.getAll();
    }
}
