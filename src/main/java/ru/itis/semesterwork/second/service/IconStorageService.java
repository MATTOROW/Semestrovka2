package ru.itis.semesterwork.second.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semesterwork.second.exception.IconServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class IconStorageService {

    @Value("${file.icon-storage}")
    private String iconStorageDir;
    private Path iconStoragePath;

    @PostConstruct
    private void init() {
        iconStoragePath = Paths.get(iconStorageDir).toAbsolutePath().normalize();
    }

    public String store(MultipartFile file, String username) {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String fileName = username + fileExtension;

        Path targetLocation = iconStoragePath.resolve(fileName);


        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IconServiceException();
        }
        return fileName;
    }

    public String contentType(String iconUrl) {
        Path filePath = iconStoragePath.resolve(iconUrl);
        try {
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            throw new IconServiceException();
        }
    }

    public Resource loadIcon(String iconUrl) {
        Path filePath = iconStoragePath.resolve(iconUrl);
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new IconServiceException();
        }
    }
}

