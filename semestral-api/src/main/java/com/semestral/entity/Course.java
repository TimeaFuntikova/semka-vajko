package com.semestral.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * Represents an online course.
 * May be associated with a user as an instructor.
 * Can have multiple lessons.
 */
@Getter
@Setter
@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    private Long Id;

    private String Name;
}