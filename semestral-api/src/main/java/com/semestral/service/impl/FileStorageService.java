/**
 * I implemented these file management methods by the tutorial:
 * https://www.youtube.com/watch?v=XUL60-Ke-L8&ab_channel=JavaTechie
 * https://www.youtube.com/watch?v=7L1BSy5pnGo&ab_channel=JavaTechie
 *
 * and its codes can be found here:
 * https://github.com/Java-Techie-jt/file-storage-service
 */

package com.semestral.service.impl;

import com.semestral.entity.FileData;
import com.semestral.entity.ImageData;
import com.semestral.entity.User;
import com.semestral.repository.impl.FileDataRepository;
import com.semestral.repository.impl.StorageRepository;
import com.semestral.utils.DatabaseUtil;
import com.semestral.utils.ImageUtil;
import com.semestral.utils.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageService {

    @Autowired
    private StorageRepository repository;

    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH = "C:\\Users\\timka\\IdeaProjects\\semestralProjectServerSide\\images";

    public String uploadImageAvatar(MultipartFile file, Long userID) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .user_id(userID).build());
            if(imageData != null) {
                return "file uploaded successfully : " + file.getOriginalFilename();
            }
            return null;
    }

    public String uploadImageCourse(MultipartFile file, Long courseId) throws IOException {
        Optional<ImageData> existingImageDataOptional = repository.findByName(file.getOriginalFilename());

        if (existingImageDataOptional.isPresent()) {
            ImageData existingImageData = existingImageDataOptional.get();
            repository.delete(existingImageData);
        }

        ImageData newImageData = ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .course_id(courseId)
                .build();

        repository.save(newImageData);
        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    public byte[] downloadImage(String filename) {
        Optional<ImageData> dbImageDataOptional = repository.findByName(filename);
        if (dbImageDataOptional.isPresent()) {
            ImageData dbImageData = dbImageDataOptional.get();
            return ImageUtil.decompressImage(dbImageData.getImageData());
        } else {
            return new byte[0];
        }
    }



    /**
     * Function responsible for storing the image in the concrete folder.
     * This will also transfer the file to that folder specified.
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if(fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    /**
     * Function that will get the desired filepath, load it deom db and convert it to bytes to get as images
     * @param filename
     * @return
     * @throws IOException
     */

    public byte[] downloadImageFromFileSystem(String filename) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(filename);
        String filePath = fileData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public String getFilename(Long courseID) throws SQLException {
        String query = "SELECT course_id, name FROM app_image_data WHERE course_id = ?";
        List<ImageData> imageDataList = DatabaseUtil.executeQuery(query, mapToRowMapperCourse(), courseID);
        if (!imageDataList.isEmpty()) {
            return imageDataList.get(0).getName();
        } else {
            return "";
        }
    }

    public boolean delete(Long user_id) throws SQLException {
        String query = "DELETE FROM app_image_data WHERE user_id = ?";
        return DatabaseUtil.enroll(query, user_id);
    }


    public String getAvatar(Long userID) throws SQLException {
        String query = "SELECT user_id, name FROM app_image_data WHERE user_id = ?";
        List<ImageData> imageDataList = DatabaseUtil.executeQuery(query, mapToRowMapperUser(), userID);
        if (!imageDataList.isEmpty()) {
            return imageDataList.get(0).getName();
        } else {
            return "";
        }
    }


    private static RowMapper<ImageData> mapToRowMapperCourse() {
        return resultSet -> {
            ImageData data = new ImageData();
            data.setCourse_id(resultSet.getLong("course_id"));
            data.setName(resultSet.getString("name"));
            return data;
        };
    }

    private static RowMapper<ImageData> mapToRowMapperUser() {
        return resultSet -> {
            ImageData data = new ImageData();
            data.setUser_id(resultSet.getLong("user_id"));
            data.setName(resultSet.getString("name"));
            return data;
        };
    }

}
