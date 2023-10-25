package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends BaseController<Film> {
    private final static LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.info("Film release date is invalid: {}.", film.getReleaseDate());
            throw new ValidationException("Film release date is invalid.");
        }
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Create film {}.", film);
        return super.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Update film {}.", film);
        return super.update(film);
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Get all films.");
        return super.getAll();
    }
}
