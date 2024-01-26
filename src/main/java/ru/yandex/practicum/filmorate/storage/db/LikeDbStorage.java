package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(Long filmId, Long userId) {
        String sql = "insert into likes values (?, ?)";
        jdbcTemplate.update(sql, userId, filmId);
        String sqlUpdateRateOfFilm = "update films set rate = rate + 1";
        jdbcTemplate.update(sqlUpdateRateOfFilm);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        String sql = "delete from likes where user_id = ? and film_id = ?";
        jdbcTemplate.update(sql, userId, filmId);
        String sqlUpdateRateOfFilm = "update films set rate = rate - 1";
        jdbcTemplate.update(sqlUpdateRateOfFilm);
    }
}
