package ru.itis.semesterwork.second.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.exception.*;
import ru.itis.semesterwork.second.model.*;
import ru.itis.semesterwork.second.repository.*;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HierarchyValidationService {

    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final SubtaskGroupRepository subtaskGroupRepository;
    private final SubtaskRepository subtaskRepository;

    public ProjectEntity validateProjectHierarchy(UUID projectId) {
        return projectRepository.findByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    public CategoryEntity validateCategoryHierarchy(UUID projectId, UUID categoryId) {
        return categoryRepository.findByInnerIdAndProject_InnerId(categoryId, projectId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    public TaskEntity validateTaskHierarchy(UUID projectId, UUID categoryId, UUID taskId) {
        return taskRepository.findByInnerIdAndCategory_InnerIdAndCategory_Project_InnerId(taskId, categoryId, projectId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public SubtaskGroupEntity validateSubtaskGroupHierarchy(UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        return subtaskGroupRepository
                .findByInnerIdAndTask_InnerIdAndTask_Category_InnerIdAndTask_Category_Project_InnerId(
                        groupId, taskId, categoryId, projectId)
                .orElseThrow(() -> new SubtaskGroupNotFoundException(groupId));
    }

    public SubtaskEntity validateSubtaskHierarchy(UUID projectId, UUID categoryId, UUID taskId, UUID groupId, UUID subtaskId) {
        return subtaskRepository
                .findByInnerIdAndSubtaskGroup_InnerIdAndSubtaskGroup_Task_InnerIdAndSubtaskGroup_Task_Category_InnerIdAndSubtaskGroup_Task_Category_Project_InnerId(
                        subtaskId, groupId, taskId, categoryId, projectId)
                .orElseThrow(() -> new SubtaskNotFoundException(subtaskId));
    }
}

