package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.semesterwork.second.model.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsername(String username);

    @Query("DELETE FROM AccountEntity acc WHERE acc.username = :username")
    @Modifying
    void deleteByUsername(@Param("username") String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsernameOrEmail(String username, String email);

    @Query("SELECT acc.accountInfoEntity.imageUrl FROM AccountEntity acc WHERE acc.username = :username")
    String getIconUrlByUsername(@Param("username") String username);
}
