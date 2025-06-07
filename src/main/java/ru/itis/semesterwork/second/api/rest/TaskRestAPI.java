package ru.itis.semesterwork.second.api.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.task.CreateTaskRequest;
import ru.itis.semesterwork.second.dto.request.task.UpdateTaskInfoRequest;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;
import ru.itis.semesterwork.second.dto.response.task.TaskFullResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskShortResponse;
import java.util.UUID;

@RequestMapping("/api/v1/projects/{projectId}/categories/{categoryId}/tasks")
public interface TaskRestAPI {

    // Получить задачу по её UUID (innerId)
    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    TaskFullResponse getByInnerId(@PathVariable("innerId") UUID innerId,
                                  @PathVariable("projectId") UUID projectId,
                                  @PathVariable("categoryId") UUID categoryId);

    // Получить список задач с пагинацией и фильтрацией по статусу и категории
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    CustomPageResponseDto<TaskShortResponse> searchTasks(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @PageableDefault(sort = "position", direction = Sort.Direction.ASC) Pageable pageable
    );

    // Создать новую задачу
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID createTask(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody CreateTaskRequest request
    );

    // Частичное обновление задачи по UUID (например, обновление статуса, описания, дедлайна)
    @PatchMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void patchTask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody UpdateTaskInfoRequest request
    );

    // Удалить задачу по UUID
    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTask(
            @PathVariable("innerId") UUID innerId,
            @PathVariable("projectId") UUID projectId,
            @PathVariable("categoryId") UUID categoryId
    );
}
