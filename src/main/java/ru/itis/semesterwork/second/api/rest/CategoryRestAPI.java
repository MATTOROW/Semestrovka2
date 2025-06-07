package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.category.CreateCategoryRequest;
import ru.itis.semesterwork.second.dto.request.category.DeleteCategoryRequest;
import ru.itis.semesterwork.second.dto.request.category.UpdateCategoryRequest;
import ru.itis.semesterwork.second.dto.response.category.CategoryResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/projects/{projectId}/categories")
public interface CategoryRestAPI {

    @Operation(summary = "Создать категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Категория создана"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на создание"),
            @ApiResponse(responseCode = "404", description = "Проект не найден"),
            @ApiResponse(
                    responseCode = "409",
                    description = "Категория с таким именем или цветом уже существует в проекте"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createCategory(
            @PathVariable UUID projectId,
            @RequestBody CreateCategoryRequest request
    );

    @Operation(summary = "Получить категории проекта")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список категорий"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к проекту"),
            @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    @GetMapping
    List<CategoryResponse> getProjectCategories(@PathVariable UUID projectId);

    @Operation(summary = "Изменить категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категория обновлена"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @PutMapping("/{categoryId}")
    void updateCategory(
            @PathVariable UUID projectId,
            @PathVariable UUID categoryId,
            @RequestBody UpdateCategoryRequest request
    );

    @Operation(summary = "Удалить категорию с перемещением всех задач в другую.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Категория удалена и задачи перенесены на данную категорию."),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Категория для удаления или переноса не найдена")
    })
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(
            @PathVariable UUID projectId,
            @PathVariable UUID categoryId,
            @RequestBody DeleteCategoryRequest request
    );
}

