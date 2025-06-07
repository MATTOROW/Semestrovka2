package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.itis.semesterwork.second.dto.request.projectmember.AddMemberRequest;
import ru.itis.semesterwork.second.dto.request.projectmember.UpdateMemberRoleRequest;
import ru.itis.semesterwork.second.dto.response.projectmember.ProjectMemberResponse;
import ru.itis.semesterwork.second.exception.AccountIsMemberConflictException;
import ru.itis.semesterwork.second.exception.OwnerExitingConflictException;
import ru.itis.semesterwork.second.exception.ProjectMemberNotFoundException;
import ru.itis.semesterwork.second.exception.ProjectNotFoundException;
import ru.itis.semesterwork.second.mapper.ProjectMemberMapper;
import ru.itis.semesterwork.second.model.ProjectEntity;
import ru.itis.semesterwork.second.model.ProjectMemberEntity;
import ru.itis.semesterwork.second.model.ProjectRole;
import ru.itis.semesterwork.second.repository.ProjectMemberRepository;
import ru.itis.semesterwork.second.repository.ProjectRepository;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;

    @Transactional
    public void addMember(UUID projectId, @Valid AddMemberRequest request) {
        ProjectEntity project = projectRepository.findWithMembersByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (project.getMembers().stream().anyMatch(member -> member.getAccount().getUsername().equals(request.username()))) {
            throw new AccountIsMemberConflictException(request.username());
        }

        ProjectMemberEntity member = projectMemberMapper.toEntity(request);
        project.addMember(member);
    }


    @Transactional
    public void updateMemberRole(UUID projectId, String username, @Valid UpdateMemberRoleRequest request) {
        ProjectEntity project = projectRepository.findWithMembersByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        ProjectMemberEntity member = project.getMembers().stream()
                .filter(m -> m.getAccount().getUsername().equals(username))
                .findAny()
                .orElseThrow(() -> new ProjectMemberNotFoundException(username));

        ProjectMemberEntity me = project.getMembers().stream()
                .filter(m -> m.getAccount().getUsername().equals(SecurityContextHelper.getCurrentUser().getUsername()))
                .findAny()
                .get();

        if (request.role().equals(ProjectRole.OWNER.name())) {
            me.setRole(ProjectRole.OWNER);
        }

        member.setRole(ProjectRole.valueOf(request.role()));

        projectMemberRepository.save(member);
        projectMemberRepository.save(me);
    }

    @Transactional
    public void deleteMember(UUID projectId, String username) {
        ProjectEntity project = projectRepository.findWithMembersByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        ProjectMemberEntity member = project.getMembers().stream()
                .filter(m -> m.getAccount().getUsername().equals(username))
                .findAny().orElseThrow(() -> new ProjectMemberNotFoundException(username));

        if (member.getRole().equals(ProjectRole.OWNER)) {
            throw new OwnerExitingConflictException();
        }

        project.getMembers().remove(member);
    }

    @Transactional
    public void exitProject(UUID projectId) {
        String username = SecurityContextHelper.getCurrentUsername();
        deleteMember(projectId, username);
    }

    @Transactional
    public void deleteAllByAccountUsername(String username) {
        projectMemberRepository.deleteAllByAccountUsername(username);
    }

    public List<ProjectMemberResponse> getAllByProjectId(UUID projectId) {
        return projectMemberRepository.findAllByProjectInnerId(projectId).stream()
                .map(projectMemberMapper::toResponse)
                .toList();
    }

    public Optional<ProjectRole> getAccountRole(UUID projectId, String username) {
        return projectMemberRepository.findProjectRoleByProjectInnerIdAndAccountUsername(projectId, username);
    }
}
