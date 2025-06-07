package ru.itis.semesterwork.second.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itis.semesterwork.second.dto.request.project.CreateProjectRequest;
import ru.itis.semesterwork.second.dto.response.project.ProjectResponse;
import ru.itis.semesterwork.second.dto.response.project.ProjectShortResponse;
import ru.itis.semesterwork.second.model.ProjectEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

    @Mapping(target = "id", source = "project.innerId")
    @Mapping(target = "currentUserRole", source = "accountRole")
    ProjectResponse toResponse(ProjectEntity project, String accountRole);

    @Mapping(target = "id", source = "innerId")
    ProjectShortResponse toShortResponse(ProjectEntity project);

    ProjectEntity toEntity(CreateProjectRequest createProjectRequest);
}
