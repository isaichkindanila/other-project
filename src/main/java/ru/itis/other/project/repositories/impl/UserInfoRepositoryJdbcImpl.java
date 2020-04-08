package ru.itis.other.project.repositories.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.other.project.models.User;
import ru.itis.other.project.models.UserInfo;
import ru.itis.other.project.repositories.UserInfoRepository;

import java.util.Objects;

@Repository
@AllArgsConstructor
@Slf4j
class UserInfoRepositoryJdbcImpl implements UserInfoRepository {

    private static final String SQL_FIND_BY_USER_ID =
            "select * from user_info where user_id = ?";

    private static final String SQL_INSERT =
            "insert into user_info(user_id, pass_hash, state) values (?, ?, ?)";

    private static final String SQL_UPDATE =
            "update user_info set user_id = ?, pass_hash = ?, state = ? where id = ?";

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<UserInfo> createRowMapper(User user) {
        return (row, index) -> UserInfo.builder()
                .id(row.getLong("id"))
                .passHash(row.getString("pass_hash"))
                .state(UserInfo.State.valueOf(row.getString("state")))
                .user(user)
                .build();
    }

    @Override
    public UserInfo findByUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("user must have assigned id");
        }

        try {
            var mapper = createRowMapper(user);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_USER_ID, mapper, user.getId());
        } catch (EmptyResultDataAccessException e) {
            log.error("user exists but it's info is missing: {}", user);
            throw new IllegalStateException("data integrity is lost (sorry)");
        }
    }

    private UserInfo insert(UserInfo info) {
        var holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});

            statement.setLong(1, info.getUser().getId());
            statement.setString(2, info.getPassHash());
            statement.setString(3, info.getState().name());

            return statement;
        }, holder);

        info.setId(Objects.requireNonNull(holder.getKey()).longValue());
        return info;
    }

    private UserInfo update(UserInfo info) {
        jdbcTemplate.update(SQL_UPDATE,
                info.getUser().getId(),
                info.getPassHash(),
                info.getState().name(),
                info.getId());

        return info;
    }

    @Override
    public UserInfo save(UserInfo info) {
        if (info.getId() == null) {
            return insert(info);
        } else {
            return update(info);
        }
    }
}
