package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetRequest;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetTokenRequest;

@RequestMapping("/api/v1/auth")
public interface PasswordRecoveryAPI {

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    void forgotPassword(@RequestBody PasswordResetTokenRequest passwordResetTokenRequest);

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    void resetPassword(@RequestBody PasswordResetRequest passwordResetRequest);
}
