package com.semestral.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "APP_POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forumId", nullable = false)
    private Forum forum;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "datePosted")
    private Date datePosted;
}
