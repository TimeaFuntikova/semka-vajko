package com.semestral.service.impl;

import com.semestral.entity.User;
import com.semestral.service.UserService;
import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.RowMapper;
import org.springframework.stereotype.Service;
import com.semestral.utils.PasswordUtil;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        List<Boolean> result = DatabaseUtil.executeQuery(DatabaseUtil.getConnection(), query,
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
        String query = "SELECT * FROM users";
        return DatabaseUtil.executeQuery(DatabaseUtil.getConnection(),query, mapUserRow);
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        return DatabaseUtil.executeQuery(DatabaseUtil.getConnection(), query, mapUserRow, username).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves firstly the instance of user from the database.
     * Then gets hashed password and calls the function to resolve password validity for this concrete user.
     * @param password
     * @param username
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
            throw new RuntimeException("Requested username is already taken choose another.");
        }

        if (newNameDemand != null) {
            userToBeUpdated.setUsername(newNameDemand);
        }

        //Password should be validated on client's side at this time -- having valid one now...
        //AND SHOULD BE UPTADED ONLY IF WAS SET
        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(newPasswordDemand, salt);

        return User.update(userToBeUpdated, newNameDemand, hashedPassword, salt);
    }

    @Override
    public User delete(User userToDelete) {
        return User.delete(userToDelete);
    }

    private static final RowMapper<User> mapUserRow = resultSet -> {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setHashedPassword(resultSet.getString("hashed_password"));
        user.setSalt(resultSet.getBytes("salt"));
        return user;
    };
}