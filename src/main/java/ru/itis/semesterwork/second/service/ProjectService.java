package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.itis.semesterwork.second.dto.request.CreateProjectRequest;
import ru.itis.semesterwork.second.dto.request.UpdateProjectRequest;
import ru.itis.semesterwork.second.dto.response.PageResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.dto.response.ProjectShortResponse;
import ru.itis.semesterwork.second.exception.ProjectNotFoundException;
import ru.itis.semesterwork.second.mapper.ProjectMapper;
import ru.itis.semesterwork.second.model.AccountEntity;
import ru.itis.semesterwork.second.model.ProjectEntity;
import ru.itis.semesterwork.second.model.ProjectMemberEntity;
import ru.itis.semesterwork.second.model.ProjectRole;
import ru.itis.semesterwork.second.repository.ProjectMemberRepository;
import ru.itis.semesterwork.second.repository.ProjectRepository;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public UUID create(@Valid CreateProjectRequest request) {
        ProjectEntity project = projectMapper.toEntity(request);
        project.getMembers().add(
                ProjectMemberEntity.builder()
                .project(project)
                .role(ProjectRole.OWNER)
                .account(SecurityContextHelper.getCurrentUser().getAccount())
                .build()
        );
        return projectRepository.save(project).getInnerId();
    }

    public ProjectResponse getByInnerId(UUID projectId) {
        ProjectEntity project = projectRepository.findWithMembersByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        AccountEntity currentAccount = SecurityContextHelper.getCurrentUser().getAccount();

        String currentRole = project.getMembers().stream()
                .filter(member -> member.getAccount().equals(currentAccount))
                .map(member -> member.getRole().name())
                .findFirst()
                .orElse("GUEST");
        return projectMapper.toResponse(project, currentRole);
    }

    @Transactional
    public void updateByInnerId(UUID projectId, @Valid UpdateProjectRequest request) {
        ProjectEntity project = projectRepository.findByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        String oldName = project.getName();
        project.setName(request.name());
        project.setDescription(request.description());
        projectRepository.save(project);
    }

    @Transactional
    public void deleteByInnerId(UUID projectId) {
        ProjectEntity project = projectRepository.findByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        projectRepository.delete(project);
    }

    @Transactional
    public void deleteAllWhereAccountOwner(String username) {
        List<ProjectEntity> projects = projectRepository
                .findAllByMembersAccountUsernameAndMembersRoleEquals(username, ProjectRole.OWNER);
        projectRepository.deleteAll(projects);
    }

    public PageResponse<ProjectShortResponse> getUserProjects(String search, Pageable page) {
        String username = SecurityContextHelper.getCurrentUser().getAccount().getUsername();
        String name = search == null ? "" : search;
        Page<ProjectShortResponse> response = projectMemberRepository
                .findAllWithProjectsByAccountUsernameAndProjectNameContainsIgnoreCase(username, page, name)
                .map(member -> projectMapper.toShortResponse(member.getProject()));
        return new PageResponse<ProjectShortResponse>(
                response.getNumber(),
                response.getSize(),
                response.getTotalElements(),
                response.getTotalPages(),
                response.getContent()
        );
    }

    public List<String> getRoles() {
        return Arrays.stream(ProjectRole.values()).map(Enum::name).toList();
    }
}
