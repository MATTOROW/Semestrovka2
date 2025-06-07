package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.CategoryRestAPI;
import ru.itis.semesterwork.second.dto.request.category.CreateCategoryRequest;
import ru.itis.semesterwork.second.dto.request.category.DeleteCategoryRequest;
import ru.itis.semesterwork.second.dto.request.category.DeleteCategoryWrapper;
import ru.itis.semesterwork.second.dto.request.category.UpdateCategoryRequest;
import ru.itis.semesterwork.second.dto.response.category.CategoryResponse;
import ru.itis.semesterwork.second.service.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CategoryRestController implements CategoryRestAPI {

    private final CategoryService categoryService;

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public UUID createCategory(UUID projectId, CreateCategoryRequest request) {
        return categoryService.create(projectId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isMember(#projectId)")
    public List<CategoryResponse> getProjectCategories(UUID projectId) {
        return categoryService.getByProjectId(projectId);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void updateCategory(UUID projectId, UUID categoryId, UpdateCategoryRequest request) {
        categoryService.updateByCategoryId(projectId, categoryId, request);
    }

    @Override
    @PreAuthorize("@projectSecurityService.isAdmin(#projectId)")
    public void deleteCategory(UUID projectId, UUID categoryId, DeleteCategoryRequest request) {
        DeleteCategoryWrapper wrapper = new DeleteCategoryWrapper(categoryId, request.newCategoryId());
        categoryService.deleteByCategoryIdWithMovingToOtherCategory(projectId, wrapper);
    }
}
