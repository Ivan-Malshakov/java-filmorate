package ru.yandex.practicum.filmorate.storage.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class InMemoryFilmStorageTest {
    InMemoryFilmStorage storage;

    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final int DURATION = 100;

    @BeforeEach
    void setUp() {
        storage = new InMemoryFilmStorage();
    }

    @Test
    void validateShouldThrowValidationExceptionWithInvalidReleaseDate() {
        Film film = Film.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .releaseDate(LocalDate.of(1800, 1, 1))
                .duration(DURATION)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> storage.validate(film));
    }

    @Test
    void validateWithValidReleaseDate() {
        Film film = Film.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .releaseDate(LocalDate.of(1900, 1, 1))
                .duration(DURATION)
                .build();
        storage.validate(film);
    }
}
