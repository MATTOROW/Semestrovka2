package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.subtask.CreateSubtaskRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtask.SubtaskResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskStatusResponse;

import java.util.List;
import java.util.UUID;

@Tag(name = "Subtask Management", description = "API для управления подзадачами")
@RequestMapping("/api/v1/projects/{projectId}/categories/{categoryId}/tasks/{taskId}/groups/{groupId}/subtask")
public interface SubtaskRestAPI {

    @Operation(summary = "Получить подзадачу", description = "Возвращает информацию о подзадаче по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Подзадача найдена",
                    content = @Content(schema = @Schema(implementation = SubtaskResponse.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к задаче"),
            @ApiResponse(responseCode = "404", description = "Подзадача не найдена")
    })
    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    SubtaskResponse getByInnerId(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId
    );

    @Operation(summary = "Получить все подзадачи", description = "Возвращает все подзадачи в указанной группе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список подзадач",
                    content = @Content(schema = @Schema(implementation = SubtaskResponse.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к группе"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<SubtaskResponse> getByGroupInnerId(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId
    );

    @Operation(summary = "Создать подзадачу", description = "Создает новую подзадачу в указанной группе")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Подзадача создана"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на создание"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID createSubtask(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId,
            @RequestBody CreateSubtaskRequest request
    );

    @Operation(summary = "Обновить подзадачу", description = "Обновляет информацию о подзадаче")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Подзадача обновлена"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование"),
            @ApiResponse(responseCode = "404", description = "Подзадача не найдена")
    })
    @PutMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void putSubtask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId,
            @RequestBody UpdateSubtaskInfoRequest request
    );

    @Operation(summary = "Удалить подзадачу", description = "Удаляет подзадачу по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Подзадача удалена"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Подзадача не найдена")
    })
    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSubtask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId
    );

    @Operation(summary = "Обновить порядок подзадач", description = "Обновляет порядок подзадач в группе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Порядок подзадач обновлен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @PatchMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    void updateOrder(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId,
            @RequestBody UpdateSubtaskOrderRequest request
    );

    @Operation(summary = "Изменить статус подзадачи", description = "Изменяет статус выполнения подзадачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус изменен",
                    content = @Content(schema = @Schema(implementation = TaskStatusResponse.class))),
            @ApiResponse(responseCode = "403", description = "Нет прав на изменение"),
            @ApiResponse(responseCode = "404", description = "Подзадача не найдена")
    })
    @PatchMapping("/{innerId}/status")
    @ResponseStatus(HttpStatus.OK)
    TaskStatusResponse changeStatus(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId,
            @RequestParam("completed") Boolean completed
    );
}

