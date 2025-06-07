package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semesterwork.second.model.SubtaskEntity;

public interface SubtaskRepository extends JpaRepository<SubtaskEntity, Long> {
}
