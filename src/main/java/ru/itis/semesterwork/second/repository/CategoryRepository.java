package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semesterwork.second.model.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByProjectId(Long projectId);
    
    Optional<CategoryEntity> findByInnerId(UUID categoryId);

    @Modifying
    @Query("DELETE FROM CategoryEntity c WHERE c.innerId = :innerId")
    void deleteByInnerId(@Param("innerId") UUID categoryId);

    boolean existsByProjectInnerIdAndInnerId(UUID projectId, UUID categoryId);
}
