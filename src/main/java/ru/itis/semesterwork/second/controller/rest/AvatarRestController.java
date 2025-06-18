package ru.itis.semesterwork.second.controller.rest;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semesterwork.second.api.rest.AvatarRestAPI;
import ru.itis.semesterwork.second.service.MinioService;

@RestController
@RequiredArgsConstructor
public class AvatarRestController implements AvatarRestAPI {

    private final MinioService minioService;

    public void uploadAvatar(@RequestParam("file") @NotNull MultipartFile file) {
        minioService.uploadAvatar(file);
    }

    public void deleteAvatar() {
        minioService.deleteAvatar();
    }

    public ResponseEntity<byte[]> getAvatar(@PathVariable("username") String username) {
        return minioService.getAvatar(username);
    }
}
