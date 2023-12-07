package com.semestral.service;

import com.semestral.entity.User;
import java.util.List;

public interface UserService {
    boolean isUsernameTaken(String username);

    User create(User user);

    boolean update(User user, String newNameDemand, String newPasswordDemand);

    List<User> getAllUsers();

    User getUserByUsername(String username);

    boolean verify(String password, String username);

    User delete(User userToDelete);
}
