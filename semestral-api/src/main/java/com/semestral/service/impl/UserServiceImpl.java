package com.semestral.service.impl;

import com.semestral.entity.User;
import com.semestral.service.UserService;
import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.RowMapper;
import org.springframework.stereotype.Service;
import com.semestral.utils.PasswordUtil;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        List<Boolean> result = DatabaseUtil.executeQuery(DatabaseUtil.getConnection(), query,
                resultSet -> resultSet.getInt(1) > 0, username);

        return !result.isEmpty() && result.get(0);
    }
    @Override
    public User create(User user) {
        if (isUsernameTaken(user.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword(), salt);

        user.setHashedPassword(hashedPassword);
        user.setSalt(salt);

        return User.create(user);
    }

    @Override
    public void saveUser(User user) {
        // Implement logic to save the user
        String query = "INSERT INTO users (custom_name, hashed_password, salt) VALUES (?, ?, ?)";
        DatabaseUtil.update(query, user.getUsername(), user.getHashedPassword(), user.getSalt());
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        return DatabaseUtil.executeQuery(DatabaseUtil.getConnection(),query, mapUserRow);
    }

    @Override
    public User getUserById(Long userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        return DatabaseUtil.executeQuery(DatabaseUtil.getConnection(), query, mapUserRow, userId).stream().findFirst().orElse(null);
    }

    private static final RowMapper<User> mapUserRow = resultSet -> {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setHashedPassword(resultSet.getString("hashed_password"));
        user.setSalt(resultSet.getBytes("salt"));
        return user;
    };
}