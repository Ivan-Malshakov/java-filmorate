package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.List;

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

    @Override
    public List<Film> getPopular(Integer count) {
        String sql = "select f.id as id, f.name, f.description, f.release_date, f.rate, f.duration, " +
                "mpa.id as mpa_id, mpa.name as mpa_name, count(l.user_id) " +
                "from films as f " +
                "join mpa on f.mpa_id = mpa.id " +
                "left outer join likes as l on l.film_id = f.id " +
                "group by f.id " +
                "order by count(l.user_id) desc " +
                "limit ?";

        return jdbcTemplate.query(sql, FilmDbStorage::createFilm, count);
    }
}
