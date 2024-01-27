package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testCreateAndFindFilmById() {
        Mpa mpa = Mpa.builder()
                .id(1L)
                .name("G")
                .build();

        Film newFilm = Film.builder()
                .id(1L)
                .name("New Film")
                .description("Description")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(10)
                .rate(1)
                .mpa(mpa)
                .genres(new LinkedHashSet<>())
                .build();
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate);
        filmDbStorage.create(newFilm);

        Film savedFilm = filmDbStorage.getData(1L);

        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }
}
