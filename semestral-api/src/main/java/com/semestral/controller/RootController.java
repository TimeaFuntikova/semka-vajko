package com.semestral.controller;

import com.semestral.entity.Course;
import com.semestral.entity.User;
import com.semestral.requests.CourseManagementRequest;
import com.semestral.requests.UserRegistrationRequest;
import com.semestral.service.CourseService;
import com.semestral.service.UserService;
import com.semestral.service.OnlineLearningPlatformService;
import com.semestral.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.lang.Long.parseLong;

/**
 * Associating service classes in order to prevent wrong SQL injections.
 * Incorporating origins = "*" - for every function just for better debug
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class RootController {

        private final OnlineLearningPlatformService onlineLearningPlatformService;
        private final UserService userService;

        private final CourseService courseService;

        private final FileStorageService fileStorageService;

        @Autowired
        public RootController(
                OnlineLearningPlatformService onlineLearningPlatformService,
                UserService userService,
                CourseService courseService,
                FileStorageService fileStorageService
        ) {
            this.onlineLearningPlatformService = onlineLearningPlatformService;
            this.userService = userService;
            this.courseService = courseService;
            this.fileStorageService = fileStorageService;
        }

    /**
     * Dummy get-request for obtaining the status of the successful application boot.
     * @return String message about the server running successfully.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/status")
    public String getMessage() {
            return onlineLearningPlatformService.getMessage();
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadedImage = fileStorageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadedImage);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{filename}")
    public ResponseEntity<?> downloadImage(@PathVariable String filename)  {
        byte[] imageData = fileStorageService.downloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/filesystem")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadedImage = fileStorageService.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadedImage);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/filesystem/{filename}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String filename) throws IOException {
        byte[] imageData = fileStorageService.downloadImageFromFileSystem(filename);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

    /**
     * Function for obtaining the id from the provided username
     * @param username
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getUserId")
      public ResponseEntity<?> getUserId(@RequestParam String username) {
        User userFromDB =  userService.getUserByUsername(username);
        Long userID =  userFromDB.getId();

        return ResponseEntity.ok(userID);
    }

    /**
     * Function for getting the username based on the provided id
     * @param courseCreatorId - id of the course creator
     * @return the info about username of the course creator
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getCourseCreatorUsernameByID")
    public ResponseEntity<?> getCourseCreatorUsernameByID(@RequestParam String courseCreatorId) {
        long userIdLong = parseLong(courseCreatorId);
        User returnedUser =  userService.getUserByID(userIdLong);
        String usernameReturned = returnedUser.getUsername();

        return ResponseEntity.ok(usernameReturned);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getUserByID")
    public ResponseEntity<?> getUserByID(@RequestParam String courseCreatorId) {
        long userIdLong = parseLong(courseCreatorId);
        User returnedUser =  userService.getUserByID(userIdLong);
        String usernameReturned = returnedUser.getUsername();

        return ResponseEntity.ok(usernameReturned);
    }

    /**
     * Function that returns the data about a record based on its ID
     * @param courseId - id of the course as the parameter
     * @return an instance of the course with all the data available in DB
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getCourseById")
    public ResponseEntity<?> getCourseById(@RequestParam String courseId) {
        try {
            Long courseIdLong = parseLong(courseId);
            Course courseFromDB =  courseService.getCourseById(courseIdLong);

            return ResponseEntity.ok(courseFromDB);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not obtain course info based on course ID.");
            }
    }
    /**
     * Function for obtaining info about whether the user is registered.
     * Obtains the data about all users in the database and by the unique name compares user registration request.
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *                            also with new username and apssword request
     * @return status of the request processed
     */
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        }  catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not load the list of users.");
        }
    }

    /**
     * Function to verify the successful login of a user.
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *      *                            also with new username and password request.
     * @return status of the request processed
     */
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationRequest registrationRequest) {
            try {
                User userToBeInserted = new User();
                LocalDate currentDate = LocalDate.now();

                userToBeInserted.setUsername(registrationRequest.getName());
                userToBeInserted.setPassword(registrationRequest.getPassword());
                userToBeInserted.setDateJoined(currentDate);

                User createdUser = userService.create(userToBeInserted);

                return ResponseEntity.ok(createdUser);
            } catch (RuntimeException e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("the request was not successful.");
            }
    }

    /**
     * Function for creating a record of the new Course created
     * @param courseRequest - consists of the data filled in the forms by the course creator
     * @return status of the course created
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/createCourse")
    public ResponseEntity<?> createCourse(@RequestBody CourseManagementRequest courseRequest) {

        try {
            Course course = new Course();
            LocalDate currentDate = LocalDate.now();
            Long userIDLong = parseLong(courseRequest.getUserID());

            course.setTitle(courseRequest.getTitle());
            course.setDescription(courseRequest.getDescription());
            course.setCategory(courseRequest.getCategory());
            course.setLevel(courseRequest.getLevel());
            course.setDateCreated(currentDate);
            course.setCreated_by_user_id(userIDLong);

            Course createdCourse = courseService.create(course);
            System.out.println(createdCourse);

            return ResponseEntity.ok(createdCourse);
         } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for creating course was not successful.");
        }
    }

    /**
     * Function for retrieving all the created courses for the user
     * @param userId - as a parameter comes the user ID
     * @return - the List of obtained courses for the user
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getAllCourses")
    public ResponseEntity<?> getAllCourses(@RequestParam String userId) {

        try {
            Long userIDLong = parseLong(userId);
            List<Course> allCoursesOfUser = courseService.getAllCourses(userIDLong);

            return ResponseEntity.ok(allCoursesOfUser);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for obtaining all courses for a user was not successful.");
        }
    }

    /**
     * Function for updating a record of the new Course created
     * @param courseRequest - consists of the data filled in the forms by the course creator
     * @return status of the course created
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/updateCourse")
    public ResponseEntity<?> updateCourse(@RequestBody CourseManagementRequest courseRequest) {
        try {
            Course course = new Course();
            LocalDate currentDate = LocalDate.now();

            Long courseIdLong = parseLong(courseRequest.getId());

            course.setTitle(courseRequest.getTitle());
            course.setDescription(courseRequest.getDescription());
            course.setCategory(courseRequest.getCategory());
            course.setLevel(courseRequest.getLevel());
            course.setLastUpdated(currentDate);
            course.setId(courseIdLong);

            boolean createdCourse = courseService.update(course);

            return ResponseEntity.ok(createdCourse);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for creating course was not successful.");
        }
    }

    /**
     * Function will delete a course based on its ID.
     * @param courseRequest - containing courseID to delete
     * @return - a bool attr if the delete operation was successful.
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteCourse")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseManagementRequest courseRequest) {
        try {
            Long courseIdLong = parseLong(courseRequest.getId());
            boolean deletedSucc = courseService.delete(courseIdLong);

            return ResponseEntity.ok(deletedSucc);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not delete the course with id: " + courseRequest.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function for retrieving all the created courses for the user
     * @return - the List of obtained courses for the user
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getAllCoursesHomepage")
    public ResponseEntity<?> getAllCoursesHomepage() {
        try {
            List<Course> allCourses = courseService.getAllCourses();

            return ResponseEntity.ok(allCourses);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for obtaining all courses was not successful.");
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
            if (registrationRequest.getNewPasswordDemand() == null) registrationRequest.setNewPasswordDemand(registrationRequest.getPassword());
            boolean updateSucessfull = userService.update(userToBeUpdated, registrationRequest.getNewNameDemand(), registrationRequest.getNewPasswordDemand());

            System.out.println("updateSuccessfull" + updateSucessfull);

            //Todo:Image processing...

            return ResponseEntity.ok(updateSucessfull);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for updating user data was not successful.");
        }
    }

    /**
     * Function to delete a user from the database permanently.
     * @param registrationRequest - a structure containing info about users name and password, optionally
     *      *                            also with new username and password request.
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

            boolean userToReturn = userService.delete(userToBeDeleted);
            System.out.println(userToReturn);
            return ResponseEntity.ok(userToReturn);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for deleting the user data was not successful.");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody CourseManagementRequest courseManagementRequest) {
        try {
            String userID = courseManagementRequest.getEnrollTemp();
            String courseID = courseManagementRequest.getId();
            LocalDate enrollmentDate = LocalDate.now();

            boolean enrollment = userService.enroll(userID, courseID, enrollmentDate);

            return ResponseEntity.ok(enrollment);

        } catch (RuntimeException | SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for enrolling of the user data for specific course was not successful.");
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/unsub")
    public ResponseEntity<?> unsub(@RequestBody CourseManagementRequest courseManagementRequest) {
        try {
            String userID = courseManagementRequest.getEnrollTemp();
            String courseID = courseManagementRequest.getId();
            LocalDate enrollmentDate = LocalDate.now();

            boolean enrollment = userService.unsub(userID, courseID, enrollmentDate);

            return ResponseEntity.ok(enrollment);

        } catch (RuntimeException | SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for enrolling of the user data for specific course was not successful.");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/enrolled")
    public ResponseEntity<?> isEnrolled(@RequestBody CourseManagementRequest courseManagementRequest) {
        try {
            Long userID = parseLong(courseManagementRequest.getEnrollTemp());
            Long courseID = parseLong(courseManagementRequest.getId());

            boolean enrollment = userService.isEnrolled(userID, courseID);

            return ResponseEntity.ok(enrollment);
        } catch (RuntimeException | SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for enrolling of the user data for specific course was not successful.");
        }
    }
}
