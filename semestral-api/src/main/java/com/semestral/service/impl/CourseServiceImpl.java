package com.semestral.service.impl;

import com.semestral.entity.Course;
import com.semestral.service.CourseService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Override
    public Course create(Course course) {
        return Course.create(course);
    }

    @Override
    public boolean update(Course course) {
        return Course.update(course);
    }

    @Override
    public List<Course> getAllCourses(Long userId) throws SQLException {
        return Course.getAllCourses(userId);
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        return Course.getAllCourses();
    }
    @Override
    public Course getCourseById(Long courseId) throws SQLException {
        return Course.getCourseById(courseId);
    }

    @Override
    public boolean delete(Long courseId) throws SQLException {
        return Course.delete(courseId);
    }

    @Override
    public boolean deleteAllForUser(Long userID) throws SQLException {
        return Course.deleteAllForUser(userID);
    }
}
