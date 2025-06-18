package ru.itis.semesterwork.second.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.semesterwork.second.model.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @EntityGraph(attributePaths = "accountInfoEntity")
    Optional<AccountEntity> findByUsername(String username);

    @Query("DELETE FROM AccountEntity acc WHERE acc.username = :username")
    @Modifying
    void deleteByUsername(@Param("username") String username);

    Page<AccountEntity> findAllByUsernameContainsIgnoreCase(String username, Pageable pageable);

    @Query("SELECT a.username FROM AccountEntity a WHERE a.email = :email")
    String getAccountEntityUsernameByEmail(@Param("email") String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsernameOrEmail(String username, String email);

    @Modifying
    @Query("UPDATE AccountEntity a SET a.hashed_password = :newPassword WHERE a.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword);
}
