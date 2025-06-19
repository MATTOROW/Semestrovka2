package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.task.CreateTaskRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskCategoryRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskInfoRequest;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;
import ru.itis.semesterwork.second.dto.response.task.TaskFullResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskShortResponse;

import java.util.UUID;

@Tag(name = "Task Management", description = "API для управления задачами")
@RequestMapping("/api/v1/projects/{projectId}/categories/{categoryId}/tasks")
public interface TaskRestAPI {

    @Operation(summary = "Получить задачу", description = "Возвращает полную информацию о задаче по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена",
                    content = @Content(schema = @Schema(implementation = TaskFullResponse.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к проекту"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    TaskFullResponse getByInnerId(@PathVariable("innerId") UUID innerId,
                                  @PathVariable("projectId") UUID projectId,
                                  @PathVariable("categoryId") UUID categoryId);

    @Operation(summary = "Поиск задач", description = "Возвращает страницу задач с пагинацией")
    @ApiResponse(responseCode = "200", description = "Список задач",
            content = @Content(schema = @Schema(implementation = CustomPageResponseDto.class)))
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    CustomPageResponseDto<TaskShortResponse> searchTasks(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PageableDefault(sort = "position", direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "Создать задачу", description = "Создает новую задачу в указанной категории")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Задача создана"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на создание"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID createTask(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody CreateTaskRequest request
    );

    @Operation(summary = "Обновить задачу", description = "Частично обновляет информацию о задаче")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача обновлена"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @PatchMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void patchTask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody UpdateTaskInfoRequest request
    );

    @Operation(summary = "Переместить задачу", description = "Перемещает задачу в другую категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача перемещена"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на перемещение"),
            @ApiResponse(responseCode = "404", description = "Задача или категория не найдена")
    })
    @PatchMapping("/{innerId}/move")
    @ResponseStatus(HttpStatus.OK)
    void moveTask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody UpdateTaskCategoryRequest request
    );

    @Operation(summary = "Удалить задачу", description = "Удаляет задачу по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача удалена"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId
    );
}
