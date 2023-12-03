package com.semestral.entity;

import com.semestral.utils.DatabaseUtil;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * User represents the Entity in the system.
 */

@Data
@Entity
@Table(name = "users")
public class User {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    private String name;
    private String hashedPassword;
    private byte[] salt;


    public static void create(User user) {
        String query = "INSERT INTO users (custom_name) VALUES ('%s')";
        DatabaseUtil.update(query, user.getName());
    }

    public static List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        return DatabaseUtil.executeQuery(query, resultSet -> {
            User user = new User();
            user.setId(resultSet.getLong("custom_id"));
            user.setName(resultSet.getString("custom_name"));
            return user;
        });
    }

    public static User getUserById(long userId) {
        String query = "SELECT * FROM users WHERE custom_id = %d";
        List<User> users = DatabaseUtil.executeQuery(query, resultSet -> {
            User user = new User();
            user.setId(resultSet.getLong("custom_id"));
            user.setName(resultSet.getString("custom_name"));
            return user;
        }, userId);

        return users.isEmpty() ? null : users.get(0);
    }

    public void update() {
        String query = "UPDATE users SET custom_name = '%s' WHERE custom_id = %d";
        DatabaseUtil.update(query, this.getName(), this.getId());
    }

    public void delete() {
        String query = "DELETE FROM users WHERE custom_id = %d";
        DatabaseUtil.delete(query, this.getId());
    }

}