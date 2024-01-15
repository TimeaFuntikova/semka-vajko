package com.semestral.entity;

import com.semestral.utils.DatabaseUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * User represents the Entity in the system.
 */

@Data
@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "profile_name")
    private String profileName;

    @Column(name = "password")
    private String password;

    @Column(name="hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "salt", nullable = false)
    private byte[] salt;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "date_joined")
    private Date dateJoined;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "user_role")
    private String userRole;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setProfileName(String profileName) {this.profileName = profileName;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setProfilePhoto(String pfp) {this.profilePhoto = pfp;}

    public void setDateJoined(Date dateJoined) {this.dateJoined = dateJoined;}

    public void setLastLogin(Date lastLogin) {this.lastLogin = lastLogin;}

    public void setUserRole(String userRole) {this.userRole = userRole;}


    public static User create(User userToBeInserted) {
        String insertQuery = "INSERT INTO app_user (username, hashed_password, salt) VALUES (?, ?, ?)";

        try {
            return DatabaseUtil.create(insertQuery, userToBeInserted.getUsername(), userToBeInserted.getHashedPassword(), userToBeInserted.getSalt());
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    public static User getUserById(long userId) {
        String query = "SELECT * FROM app_user WHERE user_id = ?";
        List<User> users = DatabaseUtil.executeQuery(DatabaseUtil.getConnection(), query, resultSet -> {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setHashedPassword(resultSet.getString("hashed_password"));
            user.setSalt(resultSet.getBytes("salt"));
            return user;
        }, userId);

        return users.isEmpty() ? null : users.get(0);
    }

    public static boolean update(User userToBeUpdated, String newNameDemand, String hashedPassword, byte[] salt) {
        if (userToBeUpdated == null) throw new IllegalArgumentException("User cannot be null");

        String query = "UPDATE app_user SET username = ?, hashed_password = ?, salt = ? WHERE user_id = ?";
        return DatabaseUtil.update(
                query,
                newNameDemand,
                hashedPassword,
                salt,
                getUserById(userToBeUpdated.getId())
                );
    }

    public static User delete(User userToDelete) {
        String query = "DELETE FROM app_user WHERE username = ?";
        return DatabaseUtil.delete(query, userToDelete.getUsername());
    }
}