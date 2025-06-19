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
import ru.itis.semesterwork.second.dto.request.subtaskgroup.CreateSubtaskGroupRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupInfoResponse;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupWithSubtasksResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskStatusResponse;

import java.util.List;
import java.util.UUID;

@Tag(name = "Subtask Group Management", description = "API для управления группами подзадач")
@RequestMapping("/api/v1/projects/{projectId}/categories/{categoryId}/tasks/{taskId}/groups")
public interface SubtaskGroupRestAPI {

    @Operation(summary = "Получить группу подзадач", description = "Возвращает информацию о группе подзадач по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Группа подзадач найдена",
                    content = @Content(schema = @Schema(implementation = SubtaskGroupInfoResponse.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к задаче"),
            @ApiResponse(responseCode = "404", description = "Группа подзадач не найдена")
    })
    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    SubtaskGroupInfoResponse getByInnerId(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId
    );

    @Operation(summary = "Получить все группы подзадач", description = "Возвращает все группы подзадач для указанной задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список групп подзадач",
                    content = @Content(schema = @Schema(implementation = SubtaskGroupWithSubtasksResponse.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к задаче"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<SubtaskGroupWithSubtasksResponse> getByTaskInnerId(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId
    );

    @Operation(summary = "Создать группу подзадач", description = "Создает новую группу подзадач в указанной задаче")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Группа подзадач создана"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на создание"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID createSubtaskGroup(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody CreateSubtaskGroupRequest request
    );

    @Operation(summary = "Обновить группу подзадач", description = "Обновляет информацию о группе подзадач")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Группа подзадач обновлена"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование"),
            @ApiResponse(responseCode = "404", description = "Группа подзадач не найдена")
    })
    @PutMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void putSubtaskGroup(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody UpdateSubtaskGroupInfoRequest request
    );

    @Operation(summary = "Удалить группу подзадач", description = "Удаляет группу подзадач по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Группа подзадач удалена"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Группа подзадач не найдена")
    })
    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSubtaskGroup(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId
    );

    @Operation(summary = "Обновить порядок групп", description = "Обновляет порядок групп подзадач в задаче")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Порядок групп обновлен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @PatchMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    void updateOrder(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody UpdateSubtaskGroupOrderRequest request
    );

    @Operation(summary = "Изменить статус группы", description = "Изменяет статус выполнения всех подзадач в группе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус изменен",
                    content = @Content(schema = @Schema(implementation = TaskStatusResponse.class))),
            @ApiResponse(responseCode = "403", description = "Нет прав на изменение"),
            @ApiResponse(responseCode = "404", description = "Группа подзадач не найдена")
    })
    @PatchMapping("/{innerId}/status")
    @ResponseStatus(HttpStatus.OK)
    TaskStatusResponse changeStatus(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestParam("completed") Boolean completed
    );
}

