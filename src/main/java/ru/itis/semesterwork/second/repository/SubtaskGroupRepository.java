package ru.itis.semesterwork.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semesterwork.second.model.SubtaskGroupEntity;

import java.util.Optional;
import java.util.UUID;

public interface SubtaskGroupRepository extends JpaRepository<SubtaskGroupEntity, Long> {

    Optional<SubtaskGroupEntity> findByInnerId(UUID innerId);
}
