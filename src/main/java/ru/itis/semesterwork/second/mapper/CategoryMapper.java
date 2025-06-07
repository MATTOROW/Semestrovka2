package ru.itis.semesterwork.second.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itis.semesterwork.second.dto.request.category.CreateCategoryRequest;
import ru.itis.semesterwork.second.dto.response.category.CategoryResponse;
import ru.itis.semesterwork.second.model.CategoryEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "id", source = "innerId")
    CategoryResponse toResponse(CategoryEntity categoryEntity);


    CategoryEntity toEntity(CreateCategoryRequest createCategoryRequest);
}
