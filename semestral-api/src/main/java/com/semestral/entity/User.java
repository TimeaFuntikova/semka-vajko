package com.semestral.entity;

import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.RowMapper;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static java.lang.Long.parseLong;

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

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "salt", nullable = false)
    private byte[] salt;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "profile_photo_data")
    private String profilePhotoData;

    @Column(name = "date_joined")
    private LocalDate dateJoined;

    @Column(name = "last_login")
    private LocalDate lastLogin;

    @Column(name = "user_role")
    private String userRole;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setProfilePhoto(String pfp) {
        this.profilePhoto = pfp;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    private static RowMapper<User> mapToRowMapper() {
        return resultSet -> {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setHashedPassword(resultSet.getString("hashed_password"));
            user.setSalt(resultSet.getBytes("salt"));
            user.setUserRole(resultSet.getString("user_role"));
            return user;
        };
    }

    public static User create(User userToBeInserted) {
        String insertQuery = "INSERT INTO app_user (username, hashed_password, salt, user_role) VALUES (?, ?, ?, ?)";
        try {
            return DatabaseUtil.executeInsert(insertQuery, new Object[]{userToBeInserted.getUsername(), userToBeInserted.getHashedPassword(), userToBeInserted.getSalt(), "enthusiast"}, mapToRowMapper());
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    public static User getUserById(long userId) throws SQLException {
        String query = "SELECT * FROM app_user WHERE user_id = ?";
        List<User> users = DatabaseUtil.executeQuery(query, mapToRowMapper(), userId);
        return users.isEmpty() ? null : users.get(0);
    }

    public static boolean update(User userToBeUpdated, String hashedPassword, byte[] salt, String userRole) {
        if (userToBeUpdated == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        try {
        String updateQuery = "UPDATE app_user SET hashed_password = ?, salt = ?, user_role = ? WHERE user_id = ?";
            return DatabaseUtil.enroll(updateQuery,
                    hashedPassword,
                    salt,
                    userRole,
                    userToBeUpdated.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public static boolean delete(User userToDelete) throws SQLException {
        String deleteQuery = "DELETE FROM app_user WHERE username = ?";
        return DatabaseUtil.enroll(deleteQuery, userToDelete.getUsername());
    }


    public static boolean enroll(Long userID, Long courseID, LocalDate enrollmentDate) throws SQLException {
        String query = "INSERT INTO app_enrollment (user_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        return DatabaseUtil.enroll(query, userID, courseID, enrollmentDate);
    }

    public static boolean unsub(Long userID, Long courseID) throws SQLException {
        String query = "DELETE FROM app_enrollment WHERE user_id = ? AND course_id = ?";
        return DatabaseUtil.enroll(query, userID, courseID);
    }

    public static boolean unsubFromAll(Long userID) throws SQLException {
        String query = "DELETE FROM app_enrollment WHERE user_id = ?";
        return DatabaseUtil.enroll(query, userID);
    }


    public static boolean isEnrolled(Long userID, Long courseID) throws SQLException {
        String query = "SELECT COUNT(1) FROM app_enrollment WHERE user_id = ? AND course_id = ?";
        return DatabaseUtil.exists(query, userID, courseID);
    }

    public static boolean markCompleted(Long userID, Long courseID, LocalDate currentDate) throws SQLException {
        String updateQuery = "UPDATE app_enrollment SET completion_status = ? WHERE user_id = ? AND course_id = ?";
        return DatabaseUtil.enroll(updateQuery, currentDate, userID, courseID);
    }

}