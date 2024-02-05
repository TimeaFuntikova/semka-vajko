package com.semestral.service.impl;

import com.semestral.entity.Course;
import com.semestral.service.CourseService;
import com.semestral.utils.DatabaseUtil;
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
    public List<Course> getAllCourses(Long userId) {
        return Course.getAllCourses(userId);
    }

    @Override
    public List<Course> getAllCourses() {
        return Course.getAllCourses();
    }
    @Override
    public Course getCourseById(Long courseId) {
        return Course.getCourseById(courseId);
    }

    @Override
    public boolean delete(Long courseId) throws SQLException {
        return Course.delete(courseId);
    }
}
