package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.SubtaskRestAPI;
import ru.itis.semesterwork.second.dto.request.subtask.CreateSubtaskRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtask.SubtaskResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskStatusResponse;
import ru.itis.semesterwork.second.service.SubtaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubtaskRestController implements SubtaskRestAPI {

    private final SubtaskService subtaskService;

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public SubtaskResponse getByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        return subtaskService.findByInnerId(innerId, projectId, categoryId, taskId, groupId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public List<SubtaskResponse> getByGroupInnerId(UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        return subtaskService.findAllByGroupInnerId(projectId, categoryId, taskId, groupId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public UUID createSubtask(UUID projectId, UUID categoryId, UUID taskId, UUID groupId, CreateSubtaskRequest request) {
        return subtaskService.create(projectId, categoryId, taskId, groupId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void putSubtask(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId, UpdateSubtaskInfoRequest request) {
        subtaskService.updateByInnerId(innerId, projectId, categoryId, taskId, groupId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void deleteSubtask(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        subtaskService.deleteByInnerId(innerId, projectId, categoryId, taskId, groupId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void updateOrder(UUID projectId, UUID categoryId, UUID taskId, UUID groupId, UpdateSubtaskOrderRequest request) {
        subtaskService.updateOrder(projectId, categoryId, taskId, groupId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public TaskStatusResponse changeStatus(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId, Boolean completed) {
        return subtaskService.changeStatus(innerId, projectId, categoryId, taskId, groupId, completed);
    }
}
