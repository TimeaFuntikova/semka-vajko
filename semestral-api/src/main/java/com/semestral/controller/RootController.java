package com.semestral.controller;

import com.semestral.entity.User;
import com.semestral.requests.UserRegistrationRequest;
import com.semestral.service.UserService;
import com.semestral.service.OnlineLearningPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Associating service classes in order to prevent wrong SQL injections.
 */
@RestController
@CrossOrigin(origins = "*")
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

    @PostMapping("/isRegistered")
    public ResponseEntity<?> isRegistered(@RequestBody UserRegistrationRequest registrationRequest) {
    String usernameFromInput = registrationRequest.getName();
            List<User> foundUsers = userService.getAllUsers();
            for(User foundUser: foundUsers) {
               if(Objects.equals(foundUser.getUsername(), usernameFromInput)) {
                   return ResponseEntity.ok(foundUser);
               }
            }
            return null; //redirect na register
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        // Validate registrationRequest (perform server-side validation)

        // Check if the username is already taken
        if (userService.isUsernameTaken(registrationRequest.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        // Save the user to the database
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationRequest registrationRequest) {
           User userToBeInserted = new User();
           userToBeInserted.setUsername(registrationRequest.getName());
           userToBeInserted.setPassword(registrationRequest.getPassword());
           userService.create(userToBeInserted);

            return ResponseEntity.ok("User successfully created.");

    }
}