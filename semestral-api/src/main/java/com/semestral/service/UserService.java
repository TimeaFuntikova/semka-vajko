package com.semestral.service;

import com.semestral.entity.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface UserService {
    boolean isUsernameTaken(String username) throws SQLException;

    User create(User user) throws SQLException;

    boolean update(User user, String newPasswordDemand) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    User getUserByUsername(String username) throws SQLException;

    User getUserByID(long userId) throws SQLException;

    boolean verify(String password, String username) throws SQLException;

    boolean delete(User userToDelete) throws SQLException;

    boolean enroll(Long userID, Long courseID, LocalDate enrollmentDate) throws SQLException;
    boolean unsub(Long userID, Long courseID) throws SQLException;
    boolean unsub(Long userID) throws SQLException;
    boolean isEnrolled(Long userID, Long courseID) throws SQLException;
}
