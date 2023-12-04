package com.semestral.entity;

import com.semestral.utils.DatabaseUtil;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.SQLException;
import java.util.List;

/**
 * User represents the Entity in the system.
 */

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "salt", nullable = false)
    private byte[] salt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public static User create(User user) {
        String insertQuery = "INSERT INTO users (username, hashed_password, salt) VALUES (?, ?, ?)";

        try {
            return DatabaseUtil.create(insertQuery, user.getUsername(), user.getHashedPassword(), user.getSalt());
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    public static List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        return DatabaseUtil.executeQuery(DatabaseUtil.getConnection(), query, resultSet -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            return user;
        });
    }

    public static User getUserById(long userId) {
        String query = "SELECT * FROM users WHERE id = %d";
        List<User> users = DatabaseUtil.executeQuery(DatabaseUtil.getConnection(), query, resultSet -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            return user;
        }, userId);

        return users.isEmpty() ? null : users.get(0);
    }

    public void update() {
        String query = "UPDATE users SET username = '%s' WHERE id = %d";
        DatabaseUtil.update(query, this.getUsername(), this.getId());
    }

    public void delete() {
        String query = "DELETE FROM users WHERE id = %d";
        DatabaseUtil.delete(query, this.getId());
    }

}