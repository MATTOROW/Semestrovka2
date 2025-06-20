package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.TaskRestAPI;
import ru.itis.semesterwork.second.dto.request.task.CreateTaskRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskCategoryRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskInfoRequest;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;
import ru.itis.semesterwork.second.dto.response.task.TaskFullResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskShortResponse;
import ru.itis.semesterwork.second.service.TaskService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskRestController implements TaskRestAPI {

    private final TaskService taskService;

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public TaskFullResponse getByInnerId(UUID innerId, UUID projectId, UUID categoryId) {
        return taskService.findByInnerId(innerId, projectId, categoryId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public CustomPageResponseDto<TaskShortResponse> searchTasks(
            UUID projectId,
            UUID categoryId,
            Pageable pageable
    ) {
        return taskService.searchTasksByCategoryId(projectId, categoryId, pageable);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public UUID createTask(UUID projectId, UUID categoryId, CreateTaskRequest request) {
        return taskService.create(projectId, categoryId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void patchTask(UUID innerId, UUID projectId, UUID categoryId, UpdateTaskInfoRequest request) {
        taskService.patchByInnerId(innerId, projectId, categoryId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void moveTask(UUID innerId, UUID projectId, UUID categoryId, UpdateTaskCategoryRequest request) {
        taskService.changeTaskCategory(innerId, projectId, categoryId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void deleteTask(UUID innerId, UUID projectId, UUID categoryId) {
        taskService.deleteByInnerId(innerId, projectId, categoryId);
    }
}
