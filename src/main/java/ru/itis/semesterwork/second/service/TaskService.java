package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.dto.request.task.CreateTaskRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskCategoryRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskInfoRequest;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;
import ru.itis.semesterwork.second.dto.response.task.TaskFullResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskShortResponse;
import ru.itis.semesterwork.second.mapper.TaskMapper;
import ru.itis.semesterwork.second.model.CategoryEntity;
import ru.itis.semesterwork.second.model.TaskEntity;
import ru.itis.semesterwork.second.model.TaskStatus;
import ru.itis.semesterwork.second.repository.CategoryRepository;
import ru.itis.semesterwork.second.repository.TaskRepository;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import java.time.Instant;
import java.util.List;
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
                page.isLast(),
                page.getContent()
        );
    }

    @Transactional
    public UUID create(UUID projectId, UUID categoryId, CreateTaskRequest request) {
        TaskEntity taskEntity = taskMapper.toEntity(request);
        taskEntity.setAuthor(SecurityContextHelper.getCurrentUser().getAccount());
        CategoryEntity category = hierarchyValidationService.validateCategoryHierarchy(projectId, categoryId);
        taskEntity.setCategory(category);
        taskEntity.setPosition(taskRepository.countByCategoryInnerId(categoryId));
        if (taskEntity.getDeadline() != null && Instant.now().compareTo(taskEntity.getDeadline()) > 0) {
            taskEntity.setStatus(TaskStatus.EXPIRED);
        }
        return taskRepository.save(taskEntity).getInnerId();
    }

    @Transactional
    public void patchByInnerId(UUID innerId, UUID projectId, UUID categoryId, UpdateTaskInfoRequest request) {
        TaskEntity task = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, innerId);
        taskMapper.updateTaskEntity(request, task);
        Instant newDeadline = task.getDeadline();

        if (newDeadline != null && Instant.now().compareTo(newDeadline) > 0) {
            if (task.getStatus().equals(TaskStatus.COMPLETED)) {
                task.setStatus(TaskStatus.COMPLETED_LATE);
            } else if (task.getStatus().equals(TaskStatus.NOT_COMPLETED)) {
                task.setStatus(TaskStatus.EXPIRED);
            }
        } else if (newDeadline != null && Instant.now().compareTo(newDeadline) <= 0) {
            if (task.getStatus().equals(TaskStatus.EXPIRED)) {
                task.setStatus(TaskStatus.NOT_COMPLETED);
            } else if (task.getStatus().equals(TaskStatus.COMPLETED_LATE)) {
                task.setStatus(TaskStatus.COMPLETED);
            }
        }

        taskRepository.save(task);
    }

    @Transactional
    public void deleteByInnerId(UUID innerId, UUID projectId, UUID categoryId) {
        TaskEntity task = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, innerId);
        Integer pos = task.getPosition();

        taskRepository.updatePositionsMinus(categoryId, pos);
        taskRepository.delete(task);
    }

    @Transactional
    public void changeTaskCategory(UUID innerId, UUID projectId, UUID categoryId, @Valid UpdateTaskCategoryRequest request) {
        TaskEntity taskEntity = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, innerId);
        Integer oldPos = taskEntity.getPosition();

        Integer newPosition = request.position();
        CategoryEntity newCategory = hierarchyValidationService.validateCategoryHierarchy(projectId, request.categoryId());

        taskEntity.setCategory(newCategory);

        taskRepository.updatePositionsMinus(categoryId, oldPos);
        taskRepository.flush();
        taskRepository.updatePositionsPlus(request.categoryId(), newPosition - 1);
        taskRepository.flush();
        taskEntity.setPosition(newPosition);
        taskRepository.save(taskEntity);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void moveAllTasks(UUID projectId, UUID categoryId, UUID newCategoryId) {
        CategoryEntity category = hierarchyValidationService.validateCategoryHierarchy(projectId, categoryId);
        CategoryEntity newCategory = hierarchyValidationService.validateCategoryHierarchy(projectId, newCategoryId);

        List<TaskEntity> taskEntities = category.getTasks();
        taskEntities.forEach(taskEntity -> {
            taskEntity.setCategory(newCategory);
            taskEntity.setPosition(taskEntity.getPosition() + newCategory.getTasks().size());
        });

        taskRepository.saveAll(taskEntities);
    }
}
