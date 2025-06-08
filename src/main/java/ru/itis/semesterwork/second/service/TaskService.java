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
import ru.itis.semesterwork.second.exception.TaskNotFoundException;
import ru.itis.semesterwork.second.mapper.TaskMapper;
import ru.itis.semesterwork.second.model.CategoryEntity;
import ru.itis.semesterwork.second.model.TaskEntity;
import ru.itis.semesterwork.second.repository.CategoryRepository;
import ru.itis.semesterwork.second.repository.TaskRepository;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import java.util.List;
import java.util.Set;
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
        taskEntity.setAuthor(SecurityContextHelper.getCurrentUser().getAccount());
        CategoryEntity category = hierarchyValidationService.validateCategoryHierarchy(projectId, categoryId);
        taskEntity.setCategory(category);
        taskEntity.setPosition(category.getTasks().size());
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
        CategoryEntity category = hierarchyValidationService.validateCategoryHierarchy(projectId, categoryId);
        TaskEntity task = category.getTasks().stream()
                .filter(t -> t.getInnerId().equals(innerId))
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException(innerId));

        List<TaskEntity> tasks = category.getTasks();
        for (TaskEntity taskEntity : tasks) {
            if (taskEntity.getPosition() > task.getPosition()) {
                taskEntity.setPosition(taskEntity.getPosition() - 1);
            }
        }
        tasks.remove(task);
        taskRepository.delete(task);
        taskRepository.saveAll(tasks);
    }

    @Transactional
    public void changeTaskCategory(UUID innerId, UUID projectId, UUID categoryId, @Valid UpdateTaskCategoryRequest request) {
        TaskEntity taskEntity = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, innerId);
        CategoryEntity oldCategory = taskEntity.getCategory();
        CategoryEntity newCategory = hierarchyValidationService.validateCategoryHierarchy(projectId, request.categoryId());
        Integer newPosition = request.position();

        System.out.println("++++++++++++++++++++==========" + newPosition);

        List<TaskEntity> oldCategoryTasks = oldCategory.getTasks();
        for (TaskEntity t : oldCategoryTasks) {
            if (t.getPosition() > taskEntity.getPosition()) {
                t.setPosition(t.getPosition() - 1);
            }
        }

        List<TaskEntity> newCategoryTasks = newCategory.getTasks();

        if (newPosition > newCategoryTasks.size()) {
            newPosition = newCategoryTasks.size() + 1;
        } else {
            for (TaskEntity t : newCategoryTasks) {
                if (t.getPosition() >= newPosition) {
                    t.setPosition(t.getPosition() + 1);
                }
            }
        }
        taskEntity.setCategory(newCategory);
        taskEntity.setPosition(newPosition);

        taskRepository.saveAll(oldCategoryTasks);
        taskRepository.saveAll(newCategoryTasks);
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
