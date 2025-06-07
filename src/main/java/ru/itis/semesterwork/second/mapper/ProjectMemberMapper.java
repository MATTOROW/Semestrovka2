package ru.itis.semesterwork.second.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itis.semesterwork.second.dto.request.projectmember.AddMemberRequest;
import ru.itis.semesterwork.second.dto.response.projectmember.ProjectMemberResponse;
import ru.itis.semesterwork.second.model.ProjectMemberEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMemberMapper {

    @Mapping(target = "role", source = "role")
    @Mapping(target = "joinedAt", source = "addedAt")
    ProjectMemberResponse toResponse(ProjectMemberEntity projectMemberEntity);


    ProjectMemberEntity toEntity(AddMemberRequest request);
}
