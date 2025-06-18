package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semesterwork.second.model.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findByToken(String token);
    PasswordResetTokenEntity findByEmail(String email);
    void deleteByEmail(String email);
}
