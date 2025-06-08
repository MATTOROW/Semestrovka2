package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semesterwork.second.model.SubtaskEntity;

import java.util.Optional;
import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<SubtaskEntity, Long> {
    Optional<SubtaskEntity> findByInnerIdAndSubtaskGroup_InnerIdAndSubtaskGroup_Task_InnerIdAndSubtaskGroup_Task_Category_InnerIdAndSubtaskGroup_Task_Category_Project_InnerId(
            UUID subtaskId, UUID groupId, UUID taskId, UUID categoryId, UUID projectId
    );

    Integer countBySubtaskGroupInnerId(UUID groupId);

    @Modifying
    @Query("UPDATE SubtaskEntity s set s.position = s.position + 1 WHERE s.subtaskGroup.innerId = :groupId AND s.position > :position")
    void updatePositionsPlus(@Param("groupId") UUID groupId, @Param("position") Integer position);

    @Modifying
    @Query("UPDATE SubtaskEntity s set s.position = s.position - 1 WHERE s.subtaskGroup.innerId = :groupId AND s.position > :position")
    void updatePositionsMinus(@Param("groupId") UUID groupId, @Param("position") Integer position);
}
