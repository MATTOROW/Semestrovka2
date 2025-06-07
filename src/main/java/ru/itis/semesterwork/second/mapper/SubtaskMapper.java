package ru.itis.semesterwork.second.mapper;

import org.mapstruct.*;
import ru.itis.semesterwork.second.dto.request.subtask.CreateSubtaskRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskInfoRequest;
import ru.itis.semesterwork.second.dto.response.subtask.SubtaskResponse;
import ru.itis.semesterwork.second.model.SubtaskEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SubtaskMapper {

    SubtaskEntity toEntity(CreateSubtaskRequest createSubtaskRequest);

    @Mapping(target = "id", source = "innerId")
    SubtaskResponse toResponse(SubtaskEntity subtaskEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubtaskEntity(UpdateSubtaskInfoRequest request, @MappingTarget SubtaskEntity taskEntity);
}
