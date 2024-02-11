package com.semestral.service.impl;

import com.semestral.entity.Course;
import com.semestral.entity.Lesson;
import com.semestral.service.LessonService;
import com.semestral.utils.RowMapper;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class LessonServiceImpl implements LessonService {
    @Override
    public boolean create (Lesson lesson) throws SQLException {
        return Lesson.create(lesson);
    }

    @Override
    public Lesson getLesson(Long courseID) throws SQLException {
        return Lesson.getLesson(courseID);
    }

    @Override
    public boolean deleteAllByCourse(Long courseID) throws SQLException {
        return Lesson.deleteAllByCourse(courseID);
    }
}
