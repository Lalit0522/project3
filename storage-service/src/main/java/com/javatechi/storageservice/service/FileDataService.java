package com.javatechi.storageservice.service;

import com.javatechi.storageservice.entity.FileData;
import com.javatechi.storageservice.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileDataService {

    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH = "C:/Users/hp/OneDrive/Desktop/Myfiles/";

    public String uploadImageFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());
        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file upload successfully : " + filePath;
        }
        return null;
    }

    public byte[] downloadImageFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        return file;
    }
}
