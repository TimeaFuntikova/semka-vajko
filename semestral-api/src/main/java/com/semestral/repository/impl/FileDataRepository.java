package com.semestral.repository.impl;

import com.semestral.entity.FileData;
import com.semestral.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Integer> {

    Optional<FileData> findByName(String filename);
}
