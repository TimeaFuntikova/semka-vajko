package com.semestral.entity;

import jakarta.persistence.GeneratedValue;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * Represents a lesson within a course.
 */
@Data
@Entity
@Table(name = "lessons")

public class Lesson {
    @Id
    private Long Id;

    private String Name;


}