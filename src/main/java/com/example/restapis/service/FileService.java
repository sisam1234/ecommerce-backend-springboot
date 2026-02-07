package com.example.restapis.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${project.image}")
    private String uploadPath;

    public String uploadImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new RuntimeException("Image is required");
        }

        if (!image.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }

        String originalFileName = image.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileName = UUID.randomUUID() + extension;

        File folder = new File(uploadPath);
        if (!folder.exists()) {
            folder.mkdirs(); // ensure uploads/products exists
        }

        File dest = new File(folder, fileName);
        image.transferTo(dest);

        return fileName; // return just the filename, store path separately if needed
    }
}