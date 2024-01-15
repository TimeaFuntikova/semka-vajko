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

    /**
     * Dummy get-request for obtaining the status of the successful application boot.
     * @return String message about the server running successfully.
     */
    @GetMapping("/status")
    public String getMessage() {
            return onlineLearningPlatformService.getMessage();
    }

    /**
     * Function for obtaining info about whether the user is registered.
     * Obtains the data about all users in the database and by the unique name compares user registration request.
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *                            also with new username and apssword request
     * @return status of the request processed
     */
    @PostMapping("/isRegistered")
    public ResponseEntity<?> isRegistered(@RequestBody UserRegistrationRequest registrationRequest) {
    String usernameFromInput = registrationRequest.getName();
            List<User> foundUsers = userService.getAllUsers();
            for(User foundUser: foundUsers) {
               if(Objects.equals(foundUser.getUsername(), usernameFromInput)) {
                   return ResponseEntity.ok(foundUser);
               }
            }
            return null;
    }

    /**
     * Function for obtaining all the users from the database.
     * @return allUsers (List<User>)
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
       List<User> allUsers = userService.getAllUsers();

       return ResponseEntity.ok(allUsers);
    }

    /**
     * Function to verify the successful login of a user.
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *      *                            also with new username and password request.
     * @return status of the request processed
     */
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

    /**
     * Function that creates a new user in the database system.
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *      *                            also with new username and apssword request.
     * @return status of the request processed
     */
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
                        .body("the request was not successful.");
            }
    }

    /**
     * Function to update user data based on the request provided
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *      *                            also with new username and apssword request.
     * @return status of the request processed
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/updateUserData")
    public ResponseEntity<?> updateUserData(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            User userToBeUpdated = userService.getUserByUsername(registrationRequest.getName());
            if (registrationRequest.getNewNameDemand() == null) registrationRequest.setNewNameDemand(registrationRequest.getName());
            //if (registrationRequest.getNewPasswordDemand() == null) registrationRequest.setNewPasswordDemand(registrationRequest.getPassword());

            boolean updateSucessfull = userService.update(userToBeUpdated, registrationRequest.getNewNameDemand(), registrationRequest.getNewPasswordDemand());

            System.out.println("updateSuccessfull" + updateSucessfull);

            return ResponseEntity.ok(updateSucessfull);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to delete a user from the database permanently.
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *      *                            also with new username and apssword request.
     * @return status of the request processed
     */
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