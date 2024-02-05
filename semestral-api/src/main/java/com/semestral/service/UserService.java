package com.semestral.service;

import com.semestral.entity.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface UserService {
    boolean isUsernameTaken(String username);

    User create(User user);

    boolean update(User user, String newNameDemand, String newPasswordDemand);

    List<User> getAllUsers();

    User getUserByUsername(String username);

    User getUserByID(long userId);

    boolean verify(String password, String username);

    boolean delete(User userToDelete);

    boolean enroll(String userID, String courseID, LocalDate enrollmentDate) throws SQLException;
    boolean unsub(String userID, String courseID, LocalDate enrollmentDate) throws SQLException;
    boolean isEnrolled(Long userID, Long courseID) throws SQLException;
}
