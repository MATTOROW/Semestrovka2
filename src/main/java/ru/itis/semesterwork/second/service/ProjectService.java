package ru.itis.semesterwork.second.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.dto.request.ProjectRequest;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.repository.ProjectRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> getAllProjects() {
        return null;
    }

    public ProjectResponse findByInnerId(UUID innerId) {
        return null;
    }

    public UUID create(ProjectRequest projectRequest) {
        return null;
    }

    public void updateByInnerId(UUID innerId, ProjectRequest projectRequest) {
    }

    public void deleteByInnerId(UUID innerId) {
    }
}
