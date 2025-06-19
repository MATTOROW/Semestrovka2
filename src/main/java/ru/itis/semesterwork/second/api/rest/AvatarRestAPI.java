package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Avatar Management", description = "API для управления аватарами")
@RequestMapping("/api/v1/avatars")
public interface AvatarRestAPI {

    @Operation(summary = "Загрузить аватар", description = "Загружает аватар для текущего пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Аватар загружен"),
            @ApiResponse(responseCode = "400", description = "Невалидный файл"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    @PostMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void uploadAvatar(@RequestParam("file") @NotNull MultipartFile file);

    @Operation(summary = "Удалить аватар", description = "Удаляет аватар текущего пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Аватар удален"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAvatar();

    @Operation(summary = "Получить аватар", description = "Возвращает аватар пользователя по username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Аватар найден",
                    content = @Content(mediaType = "image/*")),
            @ApiResponse(responseCode = "404", description = "Аватар не найден")
    })
    @GetMapping(value = "/{username}")
    ResponseEntity<byte[]> getAvatar(@PathVariable("username") String username);
}
