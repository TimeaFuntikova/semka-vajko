package com.semestral.service.impl;

import com.semestral.entity.Course;
import com.semestral.entity.User;
import com.semestral.service.UserService;
import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.RowMapper;
import org.springframework.stereotype.Service;
import com.semestral.utils.PasswordUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM app_user WHERE username = ?";
        List<Boolean> result = DatabaseUtil.executeQuery(query,
                resultSet -> resultSet.getInt(1) > 0, username);

        return !result.isEmpty() && result.get(0);
    }
    @Override
    public User create(User userToBeInserted) {
        if (isUsernameTaken(userToBeInserted.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(userToBeInserted.getPassword(), salt);

        userToBeInserted.setHashedPassword(hashedPassword);
        userToBeInserted.setSalt(salt);

        return User.create(userToBeInserted);
    }


    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM app_user";
        return DatabaseUtil.executeQuery(query, mapUserRow);
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM app_user WHERE username = ?";
        return DatabaseUtil.executeQuery(query, mapUserRow, username).stream().findFirst().orElse(null);
    }

    @Override
    public User getUserByID(long userId) {
        return User.getUserById(userId);
    }

    /**
     * Retrieves firstly the instance of user from the database.
     * Then gets hashed password and calls the function to resolve password validity for this concrete user.
     * @param password - the password provided
     * @param username - the username provided
     * @return boolean verified login
     */
    @Override
    public boolean verify(String password, String username) {
        User retrievedUser = getUserByUsername(username);
        byte[] saltFromUser = retrievedUser.getSalt();
        String storedHashPassword = retrievedUser.getHashedPassword();
        return PasswordUtil.verifyPassword(password,storedHashPassword, saltFromUser);
    }

    @Override
    public boolean update(User userToBeUpdated, String newNameDemand, String newPasswordDemand) {

        //ak sa zmeni len meno:
        if (isUsernameTaken(newNameDemand)) {
            throw new RuntimeException("Requested username is already taken, please choose another.");
        }

        if (newNameDemand != null) {
            userToBeUpdated.setUsername(newNameDemand);
        }

        //Password should be validated on client's side at this time -- having valid one now...
        //AND SHOULD BE UPTADED ONLY IF WAS SET
        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(newPasswordDemand, salt);

        String userRole = userToBeUpdated.getUserRole();

        return User.update(userToBeUpdated, newNameDemand, hashedPassword, salt, userRole);
    }

    @Override
    public boolean delete(User userToDelete) {
        return User.delete(userToDelete);
    }

    @Override
    public boolean enroll(String userID, String courseID, LocalDate enrollmentDate) throws SQLException {
        return User.enroll(userID, courseID, enrollmentDate);
    }

    @Override
    public boolean unsub(String userID, String courseID, LocalDate enrollmentDate) throws SQLException {
        return User.unsub(userID, courseID, enrollmentDate);
    }

    @Override
    public boolean isEnrolled(Long userID, Long courseID) throws SQLException {
        return User.isEnrolled(userID, courseID);
    }

    private static final RowMapper<User> mapUserRow = resultSet -> {
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setHashedPassword(resultSet.getString("hashed_password"));
        user.setSalt(resultSet.getBytes("salt"));
        return user;
    };

    private static final RowMapper<Course> mapCourseRow = resultSet -> {
        Course course = new Course();
        course.setCreated_by_user_id(resultSet.getLong("created_by_user_id"));
        return course;
    };
}