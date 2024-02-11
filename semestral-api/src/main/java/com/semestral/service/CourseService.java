package com.semestral.service;

import com.semestral.entity.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseService {

    Course create(Course course);

    boolean update(Course course);

    List<Course> getAllCourses(Long userId) throws SQLException;

    List<Course> getAllCourses() throws SQLException;

    Course getCourseById(Long courseId) throws SQLException;

    boolean delete(Long courseId) throws SQLException;

    boolean deleteAllForUser(Long userID) throws SQLException;

}
