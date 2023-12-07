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

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
       List<User> allUsers = userService.getAllUsers();

       return ResponseEntity.ok(allUsers);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody UserRegistrationRequest registrationRequest) {
           try {
               String passwordToVerify = registrationRequest.getPassword();
               String username = registrationRequest.getName();
               boolean isValid = userService.verify(passwordToVerify, username);

               return ResponseEntity.ok(isValid);
           }
           catch (RuntimeException e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Password is not valid. Try again.");
           }
      }


    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationRequest registrationRequest) {
            try {
                User userToBeInserted = new User();
                userToBeInserted.setUsername(registrationRequest.getName());
                userToBeInserted.setPassword(registrationRequest.getPassword());
                User createdUser = userService.create(userToBeInserted);

                return ResponseEntity.ok(createdUser);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Username is already taken. Please choose a different username.");
            }
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/updateUserData")
    public ResponseEntity<?> updateUserData(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            User userToBeUpdated = userService.getUserByUsername(registrationRequest.getName());
            if (registrationRequest.getNewNameDemand() == null) registrationRequest.setNewNameDemand(registrationRequest.getName());
            //if (registrationRequest.getNewPasswordDemand() == null) registrationRequest.setNewPasswordDemand(registrationRequest.getPassword());

            User userToReturn = userService.update(userToBeUpdated, registrationRequest.getNewNameDemand(), registrationRequest.getNewPasswordDemand());

            System.out.println("userToReturn" + userToReturn);

            return ResponseEntity.ok(userToReturn);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            User userToBeDeleted = userService.getUserByUsername(registrationRequest.getName());
            System.out.println(userToBeDeleted);

            if (registrationRequest.getName() == null) {
                System.out.println("Input is a blank username.");
            }

            User userToReturn = userService.delete(userToBeDeleted);
            System.out.println(userToReturn);

            return ResponseEntity.ok(userToReturn);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}