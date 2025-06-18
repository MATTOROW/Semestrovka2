package ru.itis.semesterwork.second.api.rest;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/avatars")
public interface AvatarRestAPI {

    @PostMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void uploadAvatar(@RequestParam("file") @NotNull MultipartFile file);

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAvatar();

    @GetMapping(value = "/{username}")
    ResponseEntity<byte[]> getAvatar(@PathVariable("username") String username);
}
