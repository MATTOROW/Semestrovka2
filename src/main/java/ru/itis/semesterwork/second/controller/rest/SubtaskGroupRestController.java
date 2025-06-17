package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.SubtaskGroupRestAPI;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.CreateSubtaskGroupRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupInfoResponse;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupWithSubtasksResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskStatusResponse;
import ru.itis.semesterwork.second.service.SubtaskGroupService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubtaskGroupRestController implements SubtaskGroupRestAPI {

    private final SubtaskGroupService subtaskGroupService;

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public SubtaskGroupInfoResponse getByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId) {
        return subtaskGroupService.findByInnerId(innerId, projectId, categoryId, taskId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public List<SubtaskGroupWithSubtasksResponse> getByTaskInnerId(UUID projectId, UUID categoryId, UUID taskId) {
        return subtaskGroupService.findByTaskInnerId(projectId, categoryId, taskId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public UUID createSubtaskGroup(UUID projectId, UUID categoryId, UUID taskId, CreateSubtaskGroupRequest request) {
        return subtaskGroupService.create(projectId, categoryId, taskId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void putSubtaskGroup(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UpdateSubtaskGroupInfoRequest request) {
        subtaskGroupService.updateByInnerId(innerId, projectId, categoryId, taskId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void deleteSubtaskGroup(UUID innerId, UUID projectId, UUID categoryId, UUID taskId) {
        subtaskGroupService.deleteByInnerId(innerId, projectId, categoryId, taskId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void updateOrder(UUID projectId, UUID categoryId, UUID taskId, UpdateSubtaskGroupOrderRequest request) {
        subtaskGroupService.updateOrder(projectId, categoryId, taskId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public TaskStatusResponse changeStatus(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, Boolean completed) {
        return subtaskGroupService.changeStatus(innerId, projectId, categoryId, taskId, completed);
    }
}
