package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Long firstId, Long secondId) {
        String sql = "insert into friends values (?, ?)";
        jdbcTemplate.update(sql, firstId, secondId);
    }

    @Override
    public void deleteFriend(Long firstId, Long secondId) {
        String sql = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, firstId, secondId);
    }

    @Override
    public List<User> getFriends(Long userId) {
        String sql = "select f.friend_id as id, u.email, u.login, u.name, u.birthday " +
                "from friends as f " +
                "join users as u on u.id = f.friend_id " +
                "where f.user_id = ? " +
                "group by f.friend_id";

        return jdbcTemplate.query(sql, UserDbStorage::createUser, userId);
    }

    @Override
    public List<User> getCommonFriends(Long firstId, Long secondId) {
        String sql = "select f.friend_id as id, u.email, u.login, u.name, u.birthday " +
                "from friends as f " +
                "join users as u on u.id = f.friend_id " +
                "where f.friend_id in (" +
                "select friend_id " +
                "from (" +
                "select friend_id from friends where user_id = ?)" +
                "where friend_id in (" +
                "select friend_id from friends where user_id = ?)) " +
                "group by f.friend_id";

        return jdbcTemplate.query(sql, UserDbStorage::createUser, secondId, firstId);
    }
}
