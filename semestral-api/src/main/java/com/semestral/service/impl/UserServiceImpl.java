package com.semestral.service.impl;

import com.semestral.entity.User;
import com.semestral.service.UserService;
import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE custom_name = ?";
        return DatabaseUtil.executeQuery(query, resultSet -> {
            try {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }, username).get(0);
    }

    @Override
    public void saveUser(User user) {
        // Implement logic to save the user
        String query = "INSERT INTO users (custom_name, hashed_password, salt) VALUES (?, ?, ?)";
        DatabaseUtil.update(query, user.getName(), user.getHashedPassword(), user.getSalt());
    }

    @Override
    public List<User> getAllUsers() {
        // Implement logic to get all users
        String query = "SELECT * FROM users";
        return DatabaseUtil.executeQuery(query, mapUserRow);
    }

    @Override
    public User getUserById(Long userId) {
        // Implement logic to get a user by ID
        String query = "SELECT * FROM users WHERE custom_id = ?";
        return DatabaseUtil.executeQuery(query, mapUserRow, userId).stream().findFirst().orElse(null);
    }

    private static final RowMapper<User> mapUserRow = resultSet -> {
        User user = new User();
        user.setId(resultSet.getLong("custom_id"));
        user.setName(resultSet.getString("custom_name"));
        user.setHashedPassword(resultSet.getString("hashed_password"));
        user.setSalt(resultSet.getBytes("salt"));
        return user;
    };
}