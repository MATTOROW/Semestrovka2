package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.semesterwork.second.model.AccountEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findByUsernameAndEnabledTrue(String username);

    @Query("UPDATE AccountEntity acc SET acc = :acc WHERE acc.username = :username")
    @Modifying
    void updateByUsername(@Param("username") String username, @Param("acc") AccountEntity accountEntity);

    @Query("UPDATE AccountEntity acc SET acc.enabled = false WHERE acc.username = :username")
    @Modifying
    void deleteByUsername(@Param("username") String username);
}
