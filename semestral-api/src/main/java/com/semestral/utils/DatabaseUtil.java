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

    public static boolean update(String query, Object... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof User) {
                    User userParam = (User) params[i];
                    preparedStatement.setObject(i + 1, userParam.getId(), Types.INTEGER);
                } else {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Update successful. Rows affected: " + affectedRows);
                return true;
            } else {
                System.out.println("Update failed. No rows affected.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static User delete(String query, Object... params) {
        User deletedUser = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int deletedUserId = generatedKeys.getInt(1);
                        deletedUser = User.getUserById(deletedUserId);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deletedUser;
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
