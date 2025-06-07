package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.ProjectRestAPI;
import ru.itis.semesterwork.second.dto.request.project.CreateProjectRequest;
import ru.itis.semesterwork.second.dto.request.project.UpdateProjectRequest;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;
import ru.itis.semesterwork.second.dto.response.project.ProjectResponse;
import ru.itis.semesterwork.second.dto.response.project.ProjectShortResponse;
import ru.itis.semesterwork.second.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProjectRestController implements ProjectRestAPI {

    private final ProjectService projectService;

    @Override
    public UUID createProject(CreateProjectRequest request) {
        return projectService.create(request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public ProjectResponse getProject(UUID projectId) {
        return projectService.getByInnerId(projectId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isOwner(#projectId)")
    public void updateProject(UUID projectId, UpdateProjectRequest request) {
        projectService.updateByInnerId(projectId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isOwner(#projectId)")
    public void deleteProject(UUID projectId) {
        projectService.deleteByInnerId(projectId);
    }

    @Override
    public CustomPageResponseDto<ProjectShortResponse> getUserProjects(String search, Pageable pageable) {
        return projectService.getUserProjects(search, pageable);
    }

    @Override
    public List<String> getRoles() {
        return projectService.getRoles();
    }
}
