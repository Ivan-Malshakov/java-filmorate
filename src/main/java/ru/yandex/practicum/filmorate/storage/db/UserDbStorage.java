package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User data) {
        validate(data);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = Map.of(
                "email", data.getEmail(),
                "login", data.getLogin(),
                "name", data.getName(),
                "birthday", data.getBirthday());

        data.setId(simpleJdbcInsert.executeAndReturnKey(params).longValue());
        return data;
    }

    @Override
    public User update(User data) {
        validate(data);
        long id = data.getId();

        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";
        int rowsUpdated = jdbcTemplate.update(
                sql,
                data.getEmail(),
                data.getLogin(),
                data.getName(),
                data.getBirthday(),
                id);
        if (rowsUpdated <= 0) {
            throw new DataNotFoundException(String.format("User with id = %s not found", id));
        }
        return getData(id);
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "select * from users";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::createUser);
    }

    @Override
    public User getData(Long id) {
        String sqlQuery = "select * from users where id = ?";
        List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::createUser, id);
        if (users.size() != 1) {
            throw new DataNotFoundException(String.format("User with id = %s is not single", id));
        }
        return users.get(0);
    }

    static User createUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("No user name: was set login '{}'.", user.getLogin());
        }
    }
}
