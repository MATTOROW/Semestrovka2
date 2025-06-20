package ru.itis.semesterwork.second.mapper;

import org.mapstruct.*;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.CreateSubtaskGroupRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupInfoRequest;
import ru.itis.semesterwork.second.dto.response.subtask.SubtaskResponse;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupInfoResponse;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupWithSubtasksResponse;
import ru.itis.semesterwork.second.model.SubtaskGroupEntity;
import ru.itis.semesterwork.second.validation.model.NullableField;

import java.time.Instant;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SubtaskGroupMapper {

    @Mapping(target = "completed", ignore = true)
    SubtaskGroupEntity toEntity(CreateSubtaskGroupRequest createSubtaskGroupRequest);

    @Mapping(target = "id", source = "innerId")
    SubtaskGroupInfoResponse toResponse(SubtaskGroupEntity subtaskGroupEntity);

    @Mapping(target = "id", source = "subtaskGroupEntity.innerId")
    @Mapping(target = "subtasks", source = "subtasksResponse")
    SubtaskGroupWithSubtasksResponse toResponseWithMembers(SubtaskGroupEntity subtaskGroupEntity, List<SubtaskResponse> subtasksResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubtaskGroupEntity(UpdateSubtaskGroupInfoRequest request, @MappingTarget SubtaskGroupEntity taskEntity);

    @Condition
    default boolean isPresent(NullableField<?> optional) {
        return optional.isPresent();
    }

    default String mapOptionalToString(NullableField<String> optional) {
        return optional.value();
    }

    default Instant mapOptionalToLocalDateTime(NullableField<Instant> optional) {
        return optional.value();
    }
}
