package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.itis.semesterwork.second.dto.request.CreateCategoryRequest;
import ru.itis.semesterwork.second.dto.request.DeleteCategoryWrapper;
import ru.itis.semesterwork.second.dto.request.UpdateCategoryRequest;
import ru.itis.semesterwork.second.dto.response.CategoryResponse;
import ru.itis.semesterwork.second.exception.CategoryNameOrColorDuplicateConflictException;
import ru.itis.semesterwork.second.exception.CategoryNotFoundException;
import ru.itis.semesterwork.second.exception.ProjectNotFoundException;
import ru.itis.semesterwork.second.mapper.CategoryMapper;
import ru.itis.semesterwork.second.model.CategoryEntity;
import ru.itis.semesterwork.second.model.ProjectEntity;
import ru.itis.semesterwork.second.repository.CategoryRepository;
import ru.itis.semesterwork.second.repository.ProjectRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProjectRepository projectRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public UUID create(UUID projectId, @Valid CreateCategoryRequest request) {
        CategoryEntity category = categoryMapper.toEntity(request);
        ProjectEntity project = projectRepository.findByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        boolean similarExists = project.getCategories().stream()
                .anyMatch(cat -> cat.getName().equals(category.getName()) || cat.getColor().equals(category.getColor()));

        if (similarExists) {
            throw new CategoryNameOrColorDuplicateConflictException(category.getName(), category.getColor());
        }

        category.setProject(project);
        return categoryRepository.save(category).getInnerId();
    }

    public List<CategoryResponse> getByProjectId(UUID projectId) {
        ProjectEntity project = projectRepository.findByInnerId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        return categoryRepository.findAllByProjectId(project.getId()).stream().map(categoryMapper::toResponse).toList();
    }

    @Transactional
    public void updateByCategoryId(UUID projectId, UUID categoryId, @Valid UpdateCategoryRequest request) {
        CategoryEntity category = categoryRepository.findByInnerId(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        if (!category.getProject().getInnerId().equals(projectId)) {
            throw new CategoryNotFoundException(projectId);
        }
        category.setName(request.name());
        category.setColor(request.color());
        categoryRepository.save(category);
    }


    // TODO добавить перенос тасок в другие категории
    @Transactional
    public void deleteByCategoryIdWithMovingToOtherCategory(
            UUID projectId,
            @Valid DeleteCategoryWrapper request
    ) {
        if (!categoryRepository.existsByProjectInnerIdAndInnerId(projectId, request.categoryId())) {
            throw new CategoryNotFoundException(request.categoryId());
        }

        if (!categoryRepository.existsByProjectInnerIdAndInnerId(projectId, request.newCategoryId())) {
            throw new CategoryNotFoundException(request.newCategoryId());
        }

        categoryRepository.deleteByInnerId(request.categoryId());
    }
}
