package org.palmadae.donortrack.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${file.upload.dir}")
    private String uploadDir;

    public String upload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String suffix = fileName.substring(fileName.lastIndexOf("."));

            String fileOutpuitName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + UUID.randomUUID() + suffix;

            Path path = Paths.get(uploadDir + fileOutpuitName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return fileOutpuitName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
