package ru.itis.semesterwork.second.mapper;

import org.mapstruct.*;
import ru.itis.semesterwork.second.dto.request.task.CreateTaskRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskInfoRequest;
import ru.itis.semesterwork.second.dto.response.task.TaskFullResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskShortResponse;
import ru.itis.semesterwork.second.model.TaskEntity;
import ru.itis.semesterwork.second.validation.model.NullableField;

import java.time.Instant;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "innerId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    TaskEntity toEntity(CreateTaskRequest createTaskRequest);

    @Mapping(target = "id", source = "innerId")
    TaskShortResponse toShortResponse(TaskEntity taskEntity);

    @Mapping(target = "id", source = "innerId")
    @Mapping(target = "author", source = "author.username")
    TaskFullResponse toFullResponse(TaskEntity taskEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskEntity(UpdateTaskInfoRequest request, @MappingTarget TaskEntity taskEntity);

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
