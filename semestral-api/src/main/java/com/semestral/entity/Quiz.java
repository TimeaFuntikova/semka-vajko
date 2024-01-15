package com.semestral.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "APP_QUIZ")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quizId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "moduleId", nullable = false)
    private Module module;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "totalMarks")
    private Integer totalMarks;

    @Column(name = "passingMarks")
    private Integer passingMarks;
}
