package com.semestral.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents a user's enrollment in a course.
 * Connects a user to a course.
 */
@Data
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_id")
    private Long Id;

    @Column(name = "custom_name")
    private String Name;

    @ManyToOne
    private User user;

    @ManyToOne
    private Course course;

}