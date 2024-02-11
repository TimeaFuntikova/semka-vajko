package com.semestral.controller;

import com.semestral.entity.Course;
import com.semestral.entity.Lesson;
import com.semestral.entity.User;
import com.semestral.requests.CourseManagementRequest;
import com.semestral.requests.LessonRequest;
import com.semestral.requests.UserRegistrationRequest;
import com.semestral.service.CourseService;
import com.semestral.service.LessonService;
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
        private final LessonService lessonService;

        private final FileStorageService fileStorageService;

        @Autowired
        public RootController(
                OnlineLearningPlatformService onlineLearningPlatformService,
                UserService userService,
                CourseService courseService,
                LessonService lessonService,
                FileStorageService fileStorageService
        ) {
            this.onlineLearningPlatformService = onlineLearningPlatformService;
            this.userService = userService;
            this.courseService = courseService;
            this.lessonService = lessonService;
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
    @PostMapping("/markCompleted")
    public ResponseEntity<?> markCompleted(@RequestParam String userID, @RequestParam String courseID) throws IOException, SQLException {
        Long courseIDLong = parseLong(courseID);
        Long userIDLong = parseLong(userID);
        LocalDate currentDate = LocalDate.now();

        boolean returnedLesson = userService.markCompleted(userIDLong, courseIDLong, currentDate);
        return ResponseEntity.status(HttpStatus.OK).body(returnedLesson);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getCompleted")
    public ResponseEntity<?> getCompleted(@RequestParam String userID, @RequestParam String courseID) throws IOException, SQLException {
        Long courseIDLong = parseLong(courseID);
        Long userIDLong = parseLong(userID);

        boolean returnedLesson = userService.getCompleted(userIDLong, courseIDLong);
        System.out.println(returnedLesson);
        return ResponseEntity.status(HttpStatus.OK).body(returnedLesson);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/createLesson")
    public ResponseEntity<?> createLesson(@RequestBody LessonRequest lessonRequest) throws IOException, SQLException {
       Lesson lesson = new Lesson();
       Long courseID = parseLong(lessonRequest.getCourse_id());
       lesson.setCourse_id(courseID);
       lesson.setContent(lessonRequest.getContent());
       lesson.setQuizQuestion(lessonRequest.getQuiz_question());
       lesson.setAnswer1(lessonRequest.getAnswer_1());
       lesson.setAnswer2(lessonRequest.getAnswer_2());
       lesson.setAnswer3(lessonRequest.getAnswer_3());
       lesson.setCorrect_answer_index(lessonRequest.getCorrect_answer_index());
        boolean returnedLesson = lessonService.create(lesson);
        return ResponseEntity.status(HttpStatus.OK).body(returnedLesson);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getLesson")
    public ResponseEntity<?> getLesson(@RequestParam String courseID) throws IOException, SQLException {
        Long courseIDLong = parseLong(courseID);

        Lesson returnedLesson = lessonService.getLesson(courseIDLong);
        return ResponseEntity.status(HttpStatus.OK).body(returnedLesson);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteLessons")
    public ResponseEntity<?> deleteLessons(@RequestParam String courseID) throws IOException, SQLException {
        Long courseIDLong = parseLong(courseID);

        boolean deleteSucc = lessonService.deleteAllByCourse(courseIDLong);
        return ResponseEntity.status(HttpStatus.OK).body(deleteSucc);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/uploadImageAvatar")
    public ResponseEntity<?> uploadImageAvatar(@RequestParam("image")MultipartFile file, @RequestParam("userId") String userId) throws IOException {
        Long userIdLong = parseLong(userId);
        String uploadedImage = fileStorageService.uploadImageAvatar(file, userIdLong);
        return ResponseEntity.status(HttpStatus.OK).body(uploadedImage);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/uploadImageCourse")
    public ResponseEntity<?> uploadImageCourse(@RequestParam("image")MultipartFile file, @RequestParam("courseId") String courseId) throws IOException {
        Long courseIdLong = parseLong(courseId);
        String uploadedImage = fileStorageService.uploadImageCourse(file, courseIdLong);
        return ResponseEntity.status(HttpStatus.OK).body(uploadedImage);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/{filename}")
    public ResponseEntity<?> downloadImage(@PathVariable String filename)  {
        byte[] imageData = fileStorageService.downloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getFilename")
    public ResponseEntity<?> getFilename(@RequestParam String courseId) throws SQLException {
        Long courseIdLong = parseLong(courseId);
        String filename = fileStorageService.getFilename(courseIdLong);

        return ResponseEntity.ok(filename);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAvatar")
    public ResponseEntity<?> getAvatar(@RequestParam String userId) throws SQLException {
        Long userIdLong = parseLong(userId);
        String avatar = fileStorageService.getAvatar(userIdLong);

        return ResponseEntity.ok(avatar);
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
      public ResponseEntity<?> getUserId(@RequestParam String username) throws SQLException {
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
    public ResponseEntity<?> getCourseCreatorUsernameByID(@RequestParam String courseCreatorId) throws SQLException {
        long userIdLong = parseLong(courseCreatorId);
        User returnedUser =  userService.getUserByID(userIdLong);
        String usernameReturned = returnedUser.getUsername();

        return ResponseEntity.ok(usernameReturned);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getUserByID")
    public ResponseEntity<?> getUserByID(@RequestParam String courseCreatorId) throws SQLException {
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
        } catch (RuntimeException | SQLException e) {
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
    public ResponseEntity<?> isRegistered(@RequestBody UserRegistrationRequest registrationRequest) throws SQLException {
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
        }  catch (RuntimeException | SQLException e) {
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
           catch (RuntimeException | SQLException e) {

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
            } catch (RuntimeException | SQLException e) {

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

            LocalDate currentDate = LocalDate.now();
            Long userIDLong = parseLong(courseRequest.getCreated_by_user_id());

            Course course = new Course();
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
            e.printStackTrace();
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
        } catch (RuntimeException | SQLException e) {

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
        } catch (RuntimeException | SQLException e) {

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
            if (registrationRequest.getNewPasswordDemand() == null) registrationRequest.setNewPasswordDemand(registrationRequest.getPassword());
            boolean updateSucessfull = userService.update(userToBeUpdated, registrationRequest.getNewPasswordDemand());

            System.out.println("updateSuccessfull" + updateSucessfull);

            //Todo:Image processing...

            return ResponseEntity.ok(updateSucessfull);
        } catch (RuntimeException | SQLException e) {

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
            Long userID = userToBeDeleted.getId();

            // 1. vymaz najprv enrollments
            boolean enrollmentsDeleted = userService.unsub(userID);
            System.out.println("Enrollments deleted: " + enrollmentsDeleted);

            //ziskaj vsetky kurzy, ktore ma user urobene
            List<Course> courses = courseService.getAllCourses(userID);
            System.out.println("Courses associated found: " + courses);


            if (courses != null && !courses.isEmpty()) {
                //2. Vymaz mi lekcie pre vsetky taketo kurzy ak existuju
                for (Course course : courses) {
                    boolean lessonsDeleted = lessonService.deleteAllByCourse(course.getId());
                    System.out.println("Lessons deleted for course " + course.getId() + ": " + lessonsDeleted);
                }
            }  else {
                System.out.println("No courses found or courses list is empty.");
            }

            // 3. potom kurzy ak su nejake pre daneho usera
            boolean coursesDeleted = courseService.deleteAllForUser(userID);
            System.out.println("Courses deleted: " + coursesDeleted);


            // 3. aj obrazok ak je....
            boolean picDeleted = fileStorageService.delete(userID);
            System.out.println("Picture deleted: " + picDeleted);

            // 4. vymaz nakoniec usera
            boolean userDeleted = userService.delete(userToBeDeleted);
            System.out.println("User deleted: " + userDeleted);

            if (userDeleted) {
                return ResponseEntity.ok(userDeleted);
            } else {
                return ResponseEntity.ok(userDeleted);
            }

        } catch (RuntimeException | SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for deleting the user data was not successful.");
        }
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody CourseManagementRequest courseManagementRequest) {
        try {

            Long userID = parseLong(courseManagementRequest.getEnrollTemp());
            Long courseID = parseLong(courseManagementRequest.getId());
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
            Long userID = parseLong(courseManagementRequest.getEnrollTemp());
            Long courseID = parseLong(courseManagementRequest.getId());

            System.out.println(userID);
            System.out.println(courseID);
            boolean enrollment = userService.unsub(userID, courseID);

            return ResponseEntity.ok(enrollment);
        } catch (RuntimeException | SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for unsubscribing user data from a specific course was not successful.");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/enrolled")
    public ResponseEntity<?> isEnrolled(@RequestBody CourseManagementRequest courseManagementRequest) {
        try {
            Long userID = parseLong(courseManagementRequest.getEnrollTemp());
            Long courseID = parseLong(courseManagementRequest.getId());

            boolean enrollment = userService.isEnrolled(userID, courseID);
            System.out.println(enrollment);

            return ResponseEntity.ok(enrollment);
        } catch (RuntimeException | SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request for enrolling of the user data for specific course was not successful.");
        }
    }
}
