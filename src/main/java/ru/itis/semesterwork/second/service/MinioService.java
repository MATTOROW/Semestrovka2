package ru.itis.semesterwork.second.service;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semesterwork.second.exception.AvatarNotFoundException;
import ru.itis.semesterwork.second.exception.BadRequestServiceException;
import ru.itis.semesterwork.second.exception.ServiceException;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioService {

    @Value("${minio.bucket-name}")
    private String avatarBucket;

    private final MinioClient minioClient;

    public void uploadAvatar(MultipartFile file) {
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            throw new BadRequestServiceException("File must be an image file!");
        }

        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!fileExt.matches("(png|jpg|jpeg|gif|webp)")) {
            throw new BadRequestServiceException("File must be an image file!");
        }

        String currentUsername = SecurityContextHelper.getCurrentUsername();

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(avatarBucket)
                            .object(currentUsername)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new ServiceException("Failed to upload avatar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteAvatar() {
        String currentUsername = SecurityContextHelper.getCurrentUsername();

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(avatarBucket)
                            .object(currentUsername)
                            .build()
            );
        } catch (ErrorResponseException e) {
            if (e.response().code() == 404) {
                throw new AvatarNotFoundException(currentUsername);
            }
            throw new ServiceException("Failed to delete avatar", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete avatar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> getAvatar(String username) {
        try {
            // Сначала проверяем существование объекта
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(avatarBucket)
                            .object(username)
                            .build()
            );

            try (InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(avatarBucket)
                            .object(username)
                            .build()
            )) {
                StatObjectResponse stat = minioClient.statObject(
                        StatObjectArgs.builder()
                                .bucket(avatarBucket)
                                .object(username)
                                .build()
                );

                byte[] bytes = stream.readAllBytes();
                String contentType = stat.contentType();

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(bytes);
            }
        } catch (ErrorResponseException e) {
            if (e.response().code() == 404) {
                throw new AvatarNotFoundException(username);
            }
            throw new ServiceException("Failed to get avatar", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new ServiceException("Failed to get avatar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
