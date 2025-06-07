package ru.itis.semesterwork.second.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semesterwork.second.model.TaskEntity;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {


    Optional<TaskEntity> findByInnerId(UUID innerId);

    Page<TaskEntity> findByCategoryInnerId(UUID innerId, Pageable pageable);
}
