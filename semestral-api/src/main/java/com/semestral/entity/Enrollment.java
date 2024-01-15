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
    @Column(name = "enrollmentId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    @Column(name = "enrollmentDate")
    private Date enrollmentDate;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "completionStatus")
    private String completionStatus;
}