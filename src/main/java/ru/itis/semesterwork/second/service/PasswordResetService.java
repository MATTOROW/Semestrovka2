package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetRequest;
import ru.itis.semesterwork.second.dto.request.auth.PasswordResetTokenRequest;
import ru.itis.semesterwork.second.exception.PasswordResetTokenForbiddenException;
import ru.itis.semesterwork.second.model.PasswordResetTokenEntity;
import ru.itis.semesterwork.second.repository.AccountRepository;
import ru.itis.semesterwork.second.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    private static final int EXPIRATION = 30; // минуты

    @Transactional
    public void createPasswordResetTokenForUser(PasswordResetTokenRequest request) {

        String email = request.email();

        if (!accountRepository.existsByEmail(email)) {
            return;
        }

        tokenRepository.deleteByEmail(email);

        String token = String.format("%08d", new Random().nextInt(99999999));

        PasswordResetTokenEntity resetToken = PasswordResetTokenEntity.builder()
                .email(email)
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(EXPIRATION))
                .build();
        resetToken.setEmail(email);
        resetToken.setToken(token);

        tokenRepository.save(resetToken);

        emailSender.sendEmail(email, token);
    }

    private boolean validatePasswordResetToken(String email, String token) {
        PasswordResetTokenEntity resetToken = tokenRepository.findByEmail(email);

        if (resetToken == null || !resetToken.getToken().equals(token)) {
            return false;
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            return false;
        }

        return true;
    }

    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        if (!validatePasswordResetToken(request.email(), request.code())) {
            throw new PasswordResetTokenForbiddenException();
        }

        accountRepository.updatePasswordByEmail(request.email(), passwordEncoder.encode(request.newPassword()));
    }
}
