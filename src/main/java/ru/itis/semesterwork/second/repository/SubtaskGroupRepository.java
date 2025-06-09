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

    @Query("""
        SELECT COUNT(g) = SUM(CASE WHEN g.completed = true THEN 1 ELSE 0 END)
        FROM SubtaskGroupEntity g
        WHERE g.task.id = :taskId
    """)
    boolean areAllGroupsCompletedByTaskId(@Param("taskId") Long taskId);

    @Modifying
    @Query("UPDATE SubtaskGroupEntity sg SET sg.completed = :completed WHERE sg.id = :id")
    void updateCompletedById(@Param("id") Long id, @Param("completed") boolean completed);

    @Query("SELECT t.id FROM SubtaskGroupEntity s LEFT JOIN TaskEntity t ON s.task = t WHERE s.id = :id")
    Long getTaskIdById(@Param("id") Long id);
}
