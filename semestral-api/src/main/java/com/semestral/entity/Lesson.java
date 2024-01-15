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
    @Column(name = "lessonId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "moduleID", nullable = false)
    private Module module;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "videoURL")
    private String videoURL;

    @Column(name = "additionalResources")
    private String additionalResources;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;
}