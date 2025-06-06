package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import ru.itis.semesterwork.second.model.ProjectEntity;
import ru.itis.semesterwork.second.model.ProjectRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @EntityGraph(attributePaths = {"members"})
    Optional<ProjectEntity> findWithMembersByInnerId(@Param("innerId") UUID innerId);

    Optional<ProjectEntity> findByInnerId(UUID innerId);

    Boolean existsByInnerId(UUID innerId);

    @Modifying
    void deleteByInnerId(UUID innerId);

    List<ProjectEntity> findAllByMembersAccountUsernameAndMembersRoleEquals(String username, ProjectRole role);
}
