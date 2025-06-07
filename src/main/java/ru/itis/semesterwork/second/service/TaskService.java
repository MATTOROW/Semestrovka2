package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.dto.request.task.CreateTaskRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskInfoRequest;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;
import ru.itis.semesterwork.second.dto.response.task.TaskFullResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskShortResponse;
import ru.itis.semesterwork.second.exception.CategoryNotFoundException;
import ru.itis.semesterwork.second.exception.TaskNotFoundException;
import ru.itis.semesterwork.second.mapper.TaskMapper;
import ru.itis.semesterwork.second.model.CategoryEntity;
import ru.itis.semesterwork.second.model.TaskEntity;
import ru.itis.semesterwork.second.repository.CategoryRepository;
import ru.itis.semesterwork.second.repository.TaskRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    private final HierarchyValidationService hierarchyValidationService;

    public TaskFullResponse findByInnerId(UUID innerId, UUID projectId, UUID categoryId) {
        return taskMapper.toFullResponse(
                hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, innerId)
        );
    }

    public CustomPageResponseDto<TaskShortResponse> searchTasksByCategoryId(
            UUID projectId,
            UUID categoryId,
            Pageable pageable
    ) {
        hierarchyValidationService.validateCategoryHierarchy(projectId, categoryId);
        Page<TaskShortResponse> page = taskRepository
                .findByCategoryInnerId(categoryId, pageable)
                .map(taskMapper::toShortResponse);
        return new CustomPageResponseDto<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent()
        );
    }

    @Transactional
    public UUID create(UUID projectId, UUID categoryId, CreateTaskRequest request) {
        TaskEntity taskEntity = taskMapper.toEntity(request);
        CategoryEntity category = hierarchyValidationService.validateCategoryHierarchy(projectId, categoryId);
        taskEntity.setCategory(category);
        return taskRepository.save(taskEntity).getInnerId();
    }

    @Transactional
    public void patchByInnerId(UUID innerId, UUID projectId, UUID categoryId, UpdateTaskInfoRequest request) {
        TaskEntity task = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, innerId);
        taskMapper.updateTaskEntity(request, task);
        taskRepository.save(task);
    }

    @Transactional
    public void deleteByInnerId(UUID innerId, UUID projectId, UUID categoryId) {
        TaskEntity task = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, innerId);
        taskRepository.delete(task);
    }
}
