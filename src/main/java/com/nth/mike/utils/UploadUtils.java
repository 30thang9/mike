package com.nth.mike.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class UploadUtils {
    private final ResourceLoader resourceLoader;

    private static final String UPLOAD_URL = "/images/product/";

    @Autowired
    public UploadUtils(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String uploadDir = getUploadDirectory();
        Path filePath = Path.of(uploadDir + fileName);

        try {
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to save image: " + e.getMessage());
        }

        // return getRelativeImagePath(fileName);
        return fileName;
    }

    public String getOriginalFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    public boolean isImageExists(String fileName) {
        String uploadDir = getUploadDirectory();
        Path filePath = Path.of(uploadDir + fileName);
        return Files.exists(filePath);
    }

    public void deleteImage(String fileName) throws IOException {
        String uploadDir = getUploadDirectory();
        Path filePath = Path.of(uploadDir + fileName);
        Files.deleteIfExists(filePath);
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = StringUtils.getFilenameExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName + "." + extension;
    }

    private String getUploadDirectory() {
        String resourceUrl = "file:src/main/resources/static" + UPLOAD_URL;
        Resource resource = resourceLoader.getResource(resourceUrl);
        // Resource resource =
        // resourceLoader.getResource("classpath:/static/images/product/");
        try {
            return resource.getFile().getAbsolutePath() + "/";
        } catch (IOException e) {
            throw new RuntimeException("Failed to get upload directory: " + e.getMessage());
        }
    }

    private String getRelativeImagePath(String fileName) {
        return UPLOAD_URL + fileName;
    }
}
