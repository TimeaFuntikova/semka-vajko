package com.semestral.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

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

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

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
}