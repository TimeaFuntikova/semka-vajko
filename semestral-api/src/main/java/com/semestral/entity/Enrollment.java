package com.semestral.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

/**
 * Represents a user's enrollment in a course.
 * Connects a user to a course, assciative table.
 */
@Data
@Entity
@Table(name = "APP_ENROLLMENT")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "completion_status")
    private String completionStatus;
}