package com.semestral.service;

import com.semestral.entity.User;

import java.util.List;

public interface UserService {
    boolean isUsernameTaken(String username);

    void saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long userId);
}
