package com.semestral.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a user's enrollment in a course.
 * Connects a user to a course.
 */
@Data
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    private Long Id;

    private String Name;

}