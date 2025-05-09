package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semesterwork.second.model.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
