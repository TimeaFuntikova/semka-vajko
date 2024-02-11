package com.semestral.service;

import com.semestral.entity.Lesson;

import java.sql.SQLException;

public interface LessonService {

    boolean create (Lesson lesson) throws SQLException;

    Lesson getLesson (Long courseID) throws SQLException;

    boolean deleteAllByCourse(Long courseID) throws SQLException;
}
