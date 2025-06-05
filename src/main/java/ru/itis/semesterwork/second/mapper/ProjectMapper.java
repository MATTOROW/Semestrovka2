package ru.itis.semesterwork.second.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.semesterwork.second.dto.request.CreateProjectRequest;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.model.ProjectEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProjectMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    ProjectEntity toEntity(CreateProjectRequest createProjectRequest);

    @Mapping(target = "innerId", source = "innerId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    ProjectResponse toResponse(ProjectEntity projectEntity);
}
