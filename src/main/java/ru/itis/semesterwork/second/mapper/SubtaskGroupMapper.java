package ru.itis.semesterwork.second.mapper;

import org.mapstruct.*;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.CreateSubtaskGroupRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupInfoRequest;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupResponse;
import ru.itis.semesterwork.second.model.SubtaskGroupEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SubtaskGroupMapper {

    SubtaskGroupEntity toEntity(CreateSubtaskGroupRequest createSubtaskGroupRequest);

    @Mapping(target = "id", source = "innerId")
    SubtaskGroupResponse toResponse(SubtaskGroupEntity subtaskGroupEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubtaskGroupEntity(UpdateSubtaskGroupInfoRequest request, @MappingTarget SubtaskGroupEntity taskEntity);
}
