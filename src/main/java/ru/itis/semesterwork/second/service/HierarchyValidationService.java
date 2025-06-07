package ru.itis.semesterwork.second.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.exception.*;
import ru.itis.semesterwork.second.model.*;
import ru.itis.semesterwork.second.repository.ProjectRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HierarchyValidationService {

    private final ProjectRepository projectRepository;

    public ProjectEntity validateProjectHierarchy(UUID projectId) {
        ProjectEntity project = projectRepository.findByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        return project;
    }

    public CategoryEntity validateCategoryHierarchy(UUID projectId, UUID categoryId) {
        ProjectEntity project = validateProjectHierarchy(projectId);

        CategoryEntity category = project.getCategories().stream()
                .filter(cat -> cat.getInnerId().equals(categoryId))
                .findAny()
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        return category;
    }

    public TaskEntity validateTaskHierarchy(UUID projectId, UUID categoryId, UUID taskId) {
        CategoryEntity category = validateCategoryHierarchy(projectId, categoryId);

        TaskEntity task = category.getTasks().stream()
                .filter(t -> t.getInnerId().equals(taskId))
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        return task;
    }

    public SubtaskGroupEntity validateSubtaskGroupHierarchy(UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        TaskEntity task = validateTaskHierarchy(projectId, categoryId, taskId);

        SubtaskGroupEntity group = task.getSubtaskGroups().stream()
                .filter(g -> g.getInnerId().equals(groupId))
                .findAny()
                .orElseThrow(() -> new SubtaskGroupNotFoundException(groupId));

        return group;
    }

    public SubtaskEntity validateSubtaskHierarchy(UUID projectId, UUID categoryId, UUID taskId, UUID groupId, UUID subtaskId) {
        SubtaskGroupEntity group = validateSubtaskGroupHierarchy(projectId, categoryId, taskId, groupId);

        SubtaskEntity subtask = group.getSubtasks().stream()
                .filter(s -> s.getInnerId().equals(subtaskId))
                .findAny()
                .orElseThrow(() -> new SubtaskNotFoundException(subtaskId));

        return subtask;
    }
}
