package ru.itis.semesterwork.second.service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
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
        if (Files.exists(filePath)) {
            try {
                return Files.probeContentType(filePath);
            } catch (IOException e) {
                throw new IconServiceException();
            }
        } else {
            return null;
        }
    }

    public Resource loadIcon(String iconUrl) {
        Path filePath = iconStoragePath.resolve(iconUrl);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            throw new IconServiceException();
        }
    }

    public String renameIcon(String oldIconUrl, String newName) {
        System.out.println(oldIconUrl + " " + newName);
        newName = "%s.%s".formatted(newName, FilenameUtils.getExtension(oldIconUrl));
        Path filePath = iconStoragePath.resolve(oldIconUrl);
        Path newPath = iconStoragePath.resolve(newName);
        if (Files.exists(filePath)) {
            try {
                Files.move(filePath, newPath);
            } catch (IOException e) {
                throw new IconServiceException();
            }
        }
        return newName;
    }

    public void deleteIcon(String iconUrl) {
        if (iconUrl == null) {
            return;
        }
        Path filePath = iconStoragePath.resolve(iconUrl);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new IconServiceException();
        }
    }
}

