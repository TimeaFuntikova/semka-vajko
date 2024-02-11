package com.semestral.entity;

import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.RowMapper;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.processing.SQL;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents a lesson within a course.
 */
@Data
@Entity
@Table(name = "APP_LESSON")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    @JoinColumn(name = "course_id")
    private Long course_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "videoURL")
    private String videoURL;

    @Column(name = "additional_resources")
    private String additionalResources;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "quiz_question")
    private String quizQuestion;

    @Column(name = "answer_1")
    private String answer1;

    @Column(name = "answer_2")
    private String answer2;

    @Column(name = "answer_3")
    private String answer3;

    @Column(name = "correct_answer_index")
    private String correct_answer_index;

    public static boolean create(Lesson lesson) throws SQLException {
        try {
            String insertLessonQuery = "INSERT INTO app_lesson (course_id, content, quiz_question, answer_1, answer_2, answer_3, correct_answer_index) VALUES (?, ?, ?, ?, ?, ?, ?)";
            DatabaseUtil.executeInsert(insertLessonQuery, new Object[]{
                    lesson.getCourse_id(), lesson.getContent(), lesson.getQuizQuestion(), lesson.getAnswer1(), lesson.getAnswer2(), lesson.getAnswer3(), lesson.getCorrect_answer_index()}, mapToRowMapper());
            return true;
        } catch(SQLException e) {return false;}
    }

    public static Lesson getLesson(Long courseID) throws SQLException {
        String query = "SELECT * FROM app_lesson WHERE course_id = ?";
        List<Lesson> lessons =  DatabaseUtil.executeQuery(query, mapToRowMapper(), courseID);
        return lessons.isEmpty() ? null : lessons.get(0);
    }

    public static boolean deleteAllByCourse(Long course_id) throws SQLException {
        String query = "DELETE FROM app_lesson WHERE course_id = ?";
        try {
            return DatabaseUtil.enroll(query, course_id);
        } catch (SQLException e) {
            return false;
        }
    }

    private static RowMapper<Lesson> mapToRowMapper() {
        return resultSet -> {
            Lesson lesson = new Lesson();
            lesson.setId(resultSet.getLong("lesson_id"));
            lesson.setCourse_id(resultSet.getLong("course_id"));
            lesson.setContent(resultSet.getString("content"));
            lesson.setQuizQuestion(resultSet.getString("quiz_question"));
            lesson.setAnswer1(resultSet.getString("answer_1"));
            lesson.setAnswer2(resultSet.getString("answer_2"));
            lesson.setAnswer3(resultSet.getString("answer_3"));
            lesson.setCorrect_answer_index(resultSet.getString("correct_answer_index"));
            return lesson;
        };
    }

}