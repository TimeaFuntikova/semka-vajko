package com.semestral.service;

import com.semestral.entity.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseService {

    Course create(Course course);

    boolean update(Course course);

    List<Course> getAllCourses(Long userId);

    List<Course> getAllCourses();

    Course getCourseById(Long courseId);

    boolean delete(Long courseId) throws SQLException;

}
