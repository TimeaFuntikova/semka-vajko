package com.semestral.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents a lesson within a course.
 */
@Data
@Entity
@Table(name = "lessons")

public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_id")
    private Long Id;

    @Column(name = "custom_name")
    private String Name;

    @ManyToOne
    private Course course;
}