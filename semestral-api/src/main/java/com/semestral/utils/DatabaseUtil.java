package com.semestral.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String USER = "myuser";
    private static final String PASSWORD = "mypassword";
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

    public static void create(String query, Object... params) {
        try (Statement statement = connection.createStatement()) {
            String insertQuery = String.format(query, params);
            statement.execute(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static <T> List<T> executeQuery(String query, RowMapper<T> rowMapper, Object... params) {
        List<T> results = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String selectQuery = String.format(query, params);
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    T result = rowMapper.mapRow(resultSet);
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }
}
