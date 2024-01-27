package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "select * from mpa";
        return jdbcTemplate.query(sqlQuery, this::createMpa);
    }

    @Override
    public Mpa getData(Long id) {
        String sqlQuery = "select * from mpa where id = ?";
        List<Mpa> mpas = jdbcTemplate.query(sqlQuery, this::createMpa, id);
        if (mpas.size() != 1) {
            throw new DataNotFoundException(String.format("Mpa with id = %s is not single", id));
        }
        return mpas.get(0);
    }

    @Override
    public Mpa create(Mpa data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mpa update(Mpa data) {
        throw new UnsupportedOperationException();
    }

    private Mpa createMpa(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
