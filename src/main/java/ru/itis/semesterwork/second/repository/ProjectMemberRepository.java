package ru.itis.semesterwork.second.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semesterwork.second.model.ProjectMemberEntity;
import ru.itis.semesterwork.second.model.ProjectRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long> {

    List<ProjectMemberEntity> findAllByProjectInnerId(UUID projectId);

    @Query("SELECT pm.role FROM ProjectMemberEntity pm WHERE pm.project.innerId = :projectId AND pm.account.username = :accountUsername")
    Optional<ProjectRole> findRoleByProjectInnerIdAndAccountUsername(
            @Param("projectId") UUID projectId,
            @Param("accountUsername") String accountUsername
    );

    Optional<ProjectMemberEntity> findByAccountUsernameAndProjectInnerId(String accountUsername, UUID projectId);

    @Modifying
    void deleteByProjectInnerIdAndAccountUsername(UUID projectId, String username);

    @Modifying
    void deleteAllByAccountUsername(String username);

    @EntityGraph(attributePaths = {"project"})
    Page<ProjectMemberEntity> findAllWithProjectsByAccountUsernameAndProjectNameContainsIgnoreCase(
            String username,
            Pageable pageable,
            String search
    );
}
