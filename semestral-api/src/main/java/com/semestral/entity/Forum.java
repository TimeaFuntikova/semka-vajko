package com.semestral.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "APP_FORUM")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forumId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;
}
