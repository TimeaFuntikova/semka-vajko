package com.semestral.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_file_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;

    private String name;
    private String type;
    private String filePath;
}
