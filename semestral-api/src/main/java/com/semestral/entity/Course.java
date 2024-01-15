package com.semestral.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

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
    @Column(name = "courseId")
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

    @Column(name = "dateCreated")
    private Date dateCreated;

    @Column(name = "lastUpdated")
    private Date lastUpdated;

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
    public void setDateCreated(Date dateCreated) {this.dateCreated = dateCreated;}
    public void setLastUpdated(Date lastUpdated) {this.lastUpdated = lastUpdated;}

}