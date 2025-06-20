package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.ProjectMemberRestAPI;
import ru.itis.semesterwork.second.dto.request.projectmember.AddMemberRequest;
import ru.itis.semesterwork.second.dto.request.projectmember.UpdateMemberRoleRequest;
import ru.itis.semesterwork.second.dto.response.projectmember.ProjectMemberResponse;
import ru.itis.semesterwork.second.service.ProjectMemberService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProjectMemberRestController implements ProjectMemberRestAPI {

    private final ProjectMemberService projectMemberService;

    @Override
    @PreAuthorize("@projectSecurityService.isOwner(#projectId)")
    public void addMember(UUID projectId, AddMemberRequest request) {
        projectMemberService.addMember(projectId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isOwner(#projectId)")
    public void updateMemberRole(UUID projectId, String username, UpdateMemberRoleRequest request) {
        projectMemberService.updateMemberRole(projectId, username, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isOwner(#projectId)")
    public void removeMember(UUID projectId, String username) {
        projectMemberService.deleteMember(projectId, username);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public List<ProjectMemberResponse> getProjectMembers(UUID projectId) {
        return projectMemberService.getAllByProjectId(projectId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public void exitProject(UUID projectId) {
        projectMemberService.exitProject(projectId);
    }

    @Override
    public String getRoleInProject(UUID projectId, String username) {
        return projectMemberService.getAccountRole(projectId, username).map(Enum::name).orElse("GUEST");
    }
}
