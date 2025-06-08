package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.CreateSubtaskGroupRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/projects/{projectId}/categories/{categoryId}/tasks/{taskId}/groups")
public interface SubtaskGroupRestAPI {

    // Получить группу подзадач по innerId
    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    SubtaskGroupResponse getByInnerId(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId
    );

    // Получить все группы подзадач задачи по innerId задачи
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<SubtaskGroupResponse> getByTaskInnerId(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId
    );

    // Создать новую группу подзадач в задаче
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID createSubtaskGroup(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody CreateSubtaskGroupRequest request
    );

    // Частичное обновление группы подзадач по innerId
    @PutMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void putSubtaskGroup(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody UpdateSubtaskGroupInfoRequest request
    );

    // Удалить группу подзадач по innerId
    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSubtaskGroup(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId
    );

    // Обновить порядок групп подзадач в задаче (список innerId в нужном порядке)
    @PatchMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    void updateOrder(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody UpdateSubtaskGroupOrderRequest request
    );

    @PatchMapping("/{innerId}/status")
    @ResponseStatus(HttpStatus.OK)
    void changeStatus(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("taskId") UUID taskId,
            @RequestParam("completed") Boolean completed
    );
}

