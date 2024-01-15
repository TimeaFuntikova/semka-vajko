package com.semestral.repository.impl;

import com.semestral.entity.Course;
import com.semestral.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {


}
