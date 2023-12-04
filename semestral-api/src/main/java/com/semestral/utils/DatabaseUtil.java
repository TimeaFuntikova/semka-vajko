package com.semestral.utils;

import com.semestral.entity.User;
import lombok.Getter;

import java.sql.*;
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
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isHex(String str) {
        try {
            Long.parseLong(str, 16);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static User create(String query, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            preparedStatement.executeUpdate();

            User user = new User();
            user.setUsername((String) params[0]);
            user.setHashedPassword((String) params[1]);
            user.setSalt((byte[]) params[2]);

            return user;
        }
    }





    public static void update(String query, Object... params) {
        try (Statement statement = connection.createStatement()) {
            String updateQuery = String.format(query, params);
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String query, Object... params) {
        try (Statement statement = connection.createStatement()) {
            String deleteQuery = String.format(query, params);
            statement.execute(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> executeQuery(Connection connection, String query, RowMapper<T> rowMapper, Object... params) {
        List<T> results = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
            throw new RuntimeException("Error executing SQL query", e);
        }
        return results;
    }
}
