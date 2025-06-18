package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.PasswordRecoveryAPI;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetRequest;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetTokenRequest;
import ru.itis.semesterwork.second.service.PasswordResetService;

@RestController
@RequiredArgsConstructor
public class PasswordRecoveryRestController implements PasswordRecoveryAPI {

    private final PasswordResetService passwordResetService;
    private final SecurityContextLogoutHandler logoutHandler;

    @Override
    public void forgotPassword(PasswordResetTokenRequest request) {
        passwordResetService.createPasswordResetTokenForUser(request);
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        passwordResetService.resetPassword(passwordResetRequest);
    }
}
