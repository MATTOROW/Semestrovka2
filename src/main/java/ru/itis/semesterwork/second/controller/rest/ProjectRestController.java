package ru.itis.semesterwork.second.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.ProjectRestAPI;
import ru.itis.semesterwork.second.dto.request.CreateProjectRequest;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProjectRestController implements ProjectRestAPI {

    private final ProjectService projectService;

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Override
    public ProjectResponse findByInnerId(UUID innerId) {
        return projectService.findByInnerId(innerId);
    }

    @Override
    public UUID create(CreateProjectRequest createProjectRequest) {
        return projectService.create(createProjectRequest);
    }

    @Override
    public void updateByInnerId(UUID innerId, CreateProjectRequest createProjectRequest) {
        projectService.updateByInnerId(innerId, createProjectRequest);
    }

    @Override
    public void deleteByInnerId(UUID innerId) {
        projectService.deleteByInnerId(innerId);
    }

    @Override
    public List<ProjectResponse> getAllAccountProjects(String username) {
        return projectService.getAllProjectsByUsername(username);
    }
}
