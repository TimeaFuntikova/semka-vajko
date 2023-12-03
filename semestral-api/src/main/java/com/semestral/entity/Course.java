package com.semestral.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents an online course.
 * May be associated with a user as an instructor.
 * Can have multiple lessons.
 */

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_id")
    private Long Id;

    @Column(name = "custom_name")
    private String Name;

    @Column(name = "instructor_id")
    @ManyToOne
    private User instructor;
}