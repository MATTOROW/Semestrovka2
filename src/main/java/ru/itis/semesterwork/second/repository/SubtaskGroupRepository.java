package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semesterwork.second.model.SubtaskGroupEntity;

import java.util.Optional;
import java.util.UUID;

public interface SubtaskGroupRepository extends JpaRepository<SubtaskGroupEntity, Long> {

    Optional<SubtaskGroupEntity> findByInnerId(UUID innerId);

    Optional<SubtaskGroupEntity> findByInnerIdAndTask_InnerIdAndTask_Category_InnerIdAndTask_Category_Project_InnerId(
            UUID groupId, UUID taskId, UUID categoryId, UUID projectId
    );

    Integer countByTaskInnerId(UUID innerId);

    @Modifying
    @Query("UPDATE SubtaskGroupEntity sg set sg.position = sg.position + 1 WHERE sg.task.innerId = :taskId AND sg.position > :position")
    void updatePositionsPlus(@Param("taskId") UUID taskId, @Param("position") Integer position);

    @Modifying
    @Query("UPDATE SubtaskGroupEntity sg set sg.position = sg.position - 1 WHERE sg.task.innerId = :taskId AND sg.position > :position")
    void updatePositionsMinus(@Param("taskId") UUID taskId, @Param("position") Integer position);
}
