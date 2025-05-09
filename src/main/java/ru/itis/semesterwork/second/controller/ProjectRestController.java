package ru.itis.semesterwork.second.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.ProjectRestAPI;
import ru.itis.semesterwork.second.dto.request.ProjectRequest;
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
    public UUID create(ProjectRequest projectRequest) {
        return projectService.create(projectRequest);
    }

    @Override
    public void updateByInnerId(UUID innerId, ProjectRequest projectRequest) {
        projectService.updateByInnerId(innerId, projectRequest);
    }

    @Override
    public void deleteByInnerId(UUID innerId) {
        projectService.deleteByInnerId(innerId);
    }
}
