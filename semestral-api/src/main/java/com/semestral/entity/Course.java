package com.semestral.entity;

import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.RowMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an online course.
 * May be associated with a user as an instructor.
 * Can have multiple lessons.
 */
@Getter
@Setter
@Data
@Entity
@Table(name = "APP_COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "level")
    private String level;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @Column(name = "created_by_user_id")
    private Long created_by_user_id;

    public void setCreated_by_user_id(Long created_by_user_id) {
        this.created_by_user_id = created_by_user_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String desc) {this.description = desc;}
    public void setCategory(String category) {this.category = category;}
    public void setLevel(String level) {this.level = level;}
    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}
    public void setDateCreated(LocalDate dateCreated) {this.dateCreated = dateCreated;}
    public void setLastUpdated(LocalDate lastUpdated) {this.lastUpdated = lastUpdated;}

    private static RowMapper<Course> mapToRowMapper() {
        return resultSet -> {
            Course course = new Course();
            course.setId(resultSet.getLong("course_id"));
            course.setTitle(resultSet.getString("title"));
            course.setDescription(resultSet.getString("description"));
            course.setCategory(resultSet.getString("category"));
            course.setLevel(resultSet.getString("level"));
            course.setThumbnail(resultSet.getString("thumbnail"));
            course.setCreated_by_user_id(resultSet.getLong("created_by_user_id"));
            return course;
        };
    }
    public static Course create(Course coursePar) {
        String insertCourseQuery = "INSERT INTO app_course (title, description, category, level, date_created, created_by_user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return DatabaseUtil.executeInsert(insertCourseQuery, new Object[]{
                    coursePar.getTitle(), coursePar.getDescription(), coursePar.getCategory(), coursePar.getLevel(), coursePar.getDateCreated(), coursePar.getCreated_by_user_id()}, mapToRowMapper());
        } catch (SQLException e) {
            throw new RuntimeException("Error creating course", e);
        }
    }

    public static boolean update(Course course) {
        String updateQuery = "UPDATE app_course SET title = ?, description = ?, category = ?, level = ?, thumbnail = ? WHERE course_id = ?";
        try {
           Course updatedCourse = DatabaseUtil.executeUpdateOrDelete(updateQuery,
                    new Object[] {course.getTitle(), course.getDescription(), course.getCategory(),
                    course.getLevel(), course.getThumbnail(), course.getId()}, mapToRowMapper());
            if (updatedCourse == null) return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating course", e);
        }
        return true;
    }

    public static boolean delete(Long courseId) throws SQLException {
        try {
        String deleteQuery = "DELETE FROM app_course WHERE course_id = ?";
        DatabaseUtil.executeUpdateOrDelete(deleteQuery, new Object[]{courseId}, mapToRowMapper());
        return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating course", e);
        }
    }

    public static boolean deleteAllForUser(Long userID) throws SQLException {
        try {
            String deleteQuery = "DELETE FROM app_course WHERE created_by_user_id = ?";
            return DatabaseUtil.enroll(deleteQuery,userID);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting course", e);
        }
    }

    public static List<Course> getAllCourses() throws SQLException {
        String getQuery = "SELECT * FROM app_course";
        List<Course> courses =
                DatabaseUtil.executeQuery(
                        getQuery, mapToRowMapper());

        return courses.isEmpty() ? new ArrayList<>() : courses;
    }

    public static List<Course> getAllCourses(Long userId) throws SQLException {
        String getQuery = "SELECT * FROM app_course WHERE created_by_user_id = ?";
        List<Course> courses =
                DatabaseUtil.executeQuery(
                        getQuery, mapToRowMapper(), userId);

        return courses.isEmpty() ? null : courses;
    }

    public static Course getCourseById(long courseId) throws SQLException {
        String query = "SELECT * FROM app_course WHERE course_id = ?";
        List<Course> courses =
                DatabaseUtil.executeQuery(
                        query, mapToRowMapper(), courseId);

        return courses.isEmpty() ? null : courses.get(0);
    }
}