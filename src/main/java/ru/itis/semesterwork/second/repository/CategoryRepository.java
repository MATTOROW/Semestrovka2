package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semesterwork.second.model.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByProjectId(Long projectId);
    
    Optional<CategoryEntity> findByInnerId(UUID categoryId);

    void deleteByInnerId(UUID categoryId);

    boolean existsByProjectInnerIdAndInnerId(UUID projectId, UUID categoryId);
}
