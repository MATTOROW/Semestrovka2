package ru.itis.semesterwork.second.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.semesterwork.second.model.ProjectRole;
import ru.itis.semesterwork.second.service.ProjectMemberService;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectSecurity {

    private final ProjectMemberService projectMemberService;

    public boolean hasProjectRoleAndAbove(UUID projectId, ProjectRole need) {
        Optional<ProjectRole> role = projectMemberService.getAccountRole(
                projectId,
                SecurityContextHelper.getCurrentUser().getAccount().getUsername()
        );
        return role.filter(projectRole -> projectRole.compareTo(need) >= 0).isPresent();
    }

    public boolean isMember(UUID projectId) {
        return hasProjectRoleAndAbove(projectId, ProjectRole.MEMBER);
    }

    public boolean isAdmin(UUID projectId) {
        return hasProjectRoleAndAbove(projectId, ProjectRole.ADMIN);
    }

    public boolean isOwner(UUID projectId) {
        return hasProjectRoleAndAbove(projectId, ProjectRole.OWNER);
    }
}
