package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetRequest;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetTokenRequest;

@Tag(name = "Auth Management", description = "API для аутентификации и восстановления пароля")
@RequestMapping("/api/v1/auth")
public interface PasswordRecoveryAPI {

    @Operation(summary = "Запрос на сброс пароля", description = "Инициирует процесс сброса пароля, отправляя код на email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Код отправлен"),
            @ApiResponse(responseCode = "400", description = "Невалидный email"),
            @ApiResponse(responseCode = "404", description = "Аккаунт с таким email не найден")
    })
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    void forgotPassword(@RequestBody PasswordResetTokenRequest passwordResetTokenRequest);

    @Operation(summary = "Сброс пароля", description = "Сбрасывает пароль с использованием кода подтверждения")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пароль изменен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Код не найден или истек")
    })
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    void resetPassword(@RequestBody PasswordResetRequest passwordResetRequest);
}
