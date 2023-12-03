package com.semestral.controller;

import com.semestral.entity.User;
import com.semestral.requests.UserRegistrationRequest;
import com.semestral.service.UserService;
import com.semestral.utils.PasswordUtil;
import com.semestral.service.OnlineLearningPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Associating service classes in order to prevent wrong SQL injections.
 */
@RestController
@RequestMapping("/api")
public class RootController {

        private final OnlineLearningPlatformService onlineLearningPlatformService;
        private final UserService userService;

        @Autowired
        public RootController(
                OnlineLearningPlatformService onlineLearningPlatformService,
                UserService userService
        ) {
            this.onlineLearningPlatformService = onlineLearningPlatformService;
            this.userService = userService;
        }

    @GetMapping("/status")
    public String getMessage() {
            return onlineLearningPlatformService.getMessage();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        // Validate registrationRequest (perform server-side validation)

        // Check if the username is already taken
        if (userService.isUsernameTaken(registrationRequest.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        // Generate salt and hash password
        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(registrationRequest.getPassword(), salt);

        // Create a new user
        User newUser = new User();
        newUser.setName(registrationRequest.getName());
        newUser.setHashedPassword(hashedPassword);
        newUser.setSalt(salt);

        // Save the user to the database
        userService.saveUser(newUser);

        return ResponseEntity.ok("User registered successfully");
    }
}