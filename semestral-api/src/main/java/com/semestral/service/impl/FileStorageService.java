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
import com.semestral.repository.impl.FileDataRepository;
import com.semestral.repository.impl.StorageRepository;
import com.semestral.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileStorageService {

    @Autowired
    private StorageRepository repository;

    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH = "C:\\Users\\timka\\IdeaProjects\\semestralProjectServerSide\\images";

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());
            if(imageData != null) {
                return "file uploaded successfully : " + file.getOriginalFilename();
            }
            return null;
    }

    public byte[] downloadImage(String filename) {
        Optional<ImageData> dbImageData = repository.findByName(filename);
        byte[] images = ImageUtil.decompressImage(dbImageData.get().getImageData());
        return images;
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
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}
