package com.semestral.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_image_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "image_id")
    private Long image_id;

    private Long user_id;
    private Long course_id;
    private String name;
    private String type;

    @Column(name = "image_data",length = 1000)
    private byte[] imageData;

}
