package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;

@Component
@Slf4j
public class InMemoryFilmStorage extends InMemoryBaseStorage<Film> implements FilmStorage {
    private static final LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public InMemoryFilmStorage() {
    }

    @Override
    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.info("Film release date is invalid: {}.", film.getReleaseDate());
            throw new ValidationException("Film release date is invalid.");
        }
    }

}
