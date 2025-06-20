package ru.itis.semesterwork.second.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semesterwork.second.model.TaskEntity;
import ru.itis.semesterwork.second.model.TaskStatus;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {


    Optional<TaskEntity> findByInnerId(UUID innerId);

    Page<TaskEntity> findByCategoryInnerId(UUID innerId, Pageable pageable);

    Integer countByCategoryInnerId(UUID innerId);

    @Modifying
    @Query("UPDATE TaskEntity t set t.position = t.position + 1 WHERE t.category.innerId = :categoryId AND t.position > :position")
    void updatePositionsPlus(@Param("categoryId") UUID categoryId, @Param("position") Integer position);

    @Modifying
    @Query("UPDATE TaskEntity t set t.position = t.position - 1 WHERE t.category.innerId = :categoryId AND t.position > :position")
    void updatePositionsMinus(@Param("categoryId") UUID categoryId, @Param("position") Integer position);

    Optional<TaskEntity> findByInnerIdAndCategory_InnerIdAndCategory_Project_InnerId(UUID taskId, UUID categoryId, UUID projectId);

    @Modifying
    @Query("UPDATE TaskEntity t SET t.status = :status WHERE t.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") TaskStatus status);

    @Query("SELECT t.deadline FROM TaskEntity t WHERE t.id = :id")
    Instant getDeadlineById(@Param("id") Long id);

}
