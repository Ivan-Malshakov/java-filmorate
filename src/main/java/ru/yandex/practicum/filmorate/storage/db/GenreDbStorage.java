package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "select * from genres";
        return jdbcTemplate.query(sqlQuery, this::createGenre);
    }

    @Override
    public Genre getData(Long id) {
        String sqlQuery = "select * from genres where id = ?";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, this::createGenre, id);
        if (genres.size() != 1) {
            throw new DataNotFoundException(String.format("Genre with id = %s is not single", id));
        }
        return genres.get(0);
    }

    @Override
    public Genre create(Genre data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Genre update(Genre data) {
        throw new UnsupportedOperationException();
    }

    private Genre createGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
