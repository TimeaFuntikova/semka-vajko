package com.semestral.utils;

import com.semestral.entity.Course;
import com.semestral.entity.User;
import lombok.Getter;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String USER = "myuser";
    private static final String PASSWORD = "mypassword";
    @Getter
    private static Connection connection;
    private static Statement statement;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static <T> T executeInsert(String query, Object[] params, RowMapper<T> rowMapper) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(preparedStatement, params);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows);

            if (affectedRows == 0) {
                throw new SQLException("Creating object failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return rowMapper.mapRow(generatedKeys);
                } else {
                    throw new SQLException("Creating object failed, no ID obtained.");
                }

            }
        }

    }

    private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }

    public static <T> T executeUpdateOrDelete(String query, Object[] params, RowMapper<T> rowMapper) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(preparedStatement, params);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return rowMapper.mapRow(generatedKeys);
                    }
                }
            }
        }
        throw new SQLException("Executing UPDATE/DELETE failed, no ID obtained.");
    }




    public static <T> List<T> executeQuery(String query, RowMapper<T> rowMapper) throws SQLException {
        return executeQuery(query, rowMapper, new Object[0] );
    }


    public static <T> List<T> executeQuery(String query, RowMapper<T> rowMapper, Object...params) throws SQLException {
        List<T> results = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    T result = rowMapper.mapRow(resultSet);
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query: " + query, e);
        }

        return results;
    }


    public static boolean enroll(String query, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            System.out.println(preparedStatement);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows);

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query = enroll to course: " + query, e);
        }
    }


    public static boolean exists(String query, Object... params) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public static boolean getCompleted(Long userID, Long courseID) throws SQLException {
        String query = "SELECT completion_status FROM app_enrollment WHERE user_id = ? AND course_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userID);
            preparedStatement.setLong(2, courseID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getString("completion_status") != null;
            }
        }
    }

}
