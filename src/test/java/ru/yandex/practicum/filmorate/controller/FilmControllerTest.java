package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {
    private FilmController controller;

    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final int DURATION = 100;

    @BeforeEach
    void setUp() {
        controller = new FilmController();
    }

    @Test
    void validateShouldThrowValidationExceptionWithInvalidReleaseDate() {
        Film film = Film.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .releaseDate(LocalDate.of(1800, 1, 1))
                .duration(DURATION)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> controller.validate(film));
    }

    @Test
    void validateWithValidReleaseDate() {
        Film film = Film.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .releaseDate(LocalDate.of(1900, 1, 1))
                .duration(DURATION)
                .build();
        controller.validate(film);
    }
}
