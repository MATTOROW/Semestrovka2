package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.subtask.CreateSubtaskRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtask.SubtaskResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/projects/{projectId}/categories/{categoryId}/tasks/{taskId}/groups/{groupId}")
public interface SubtaskRestAPI {

    // Получить подзадачу по innerId
    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    SubtaskResponse getByInnerId(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId
    );

    // Получить все подзадачи группы подзадач по innerId группы
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<SubtaskResponse> getByGroupInnerId(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId
    );

    // Создать новую подзадачу в группе
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID createSubtask(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId,
            @RequestBody CreateSubtaskRequest request
    );

    // Частичное обновление подзадачи по innerId
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

    // Удалить подзадачу по innerId
    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSubtask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId
    );

    // Обновить порядок подзадач в группе (список innerId в нужном порядке)
    @PatchMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    void updateOrder(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @PathVariable("groupId") UUID groupId,
            @RequestBody UpdateSubtaskOrderRequest request
    );
}

