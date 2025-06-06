package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.CreateProjectRequest;
import ru.itis.semesterwork.second.dto.request.UpdateProjectRequest;
import ru.itis.semesterwork.second.dto.response.PageResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.dto.response.ProjectShortResponse;

import java.util.List;
import java.util.UUID;

@Tag(name = "Project Management", description = "API для управления проектами")
@RequestMapping("/api/v1/projects")
public interface ProjectRestAPI {

    @Operation(summary = "Создать проект")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Проект успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createProject(@RequestBody CreateProjectRequest request);

    @Operation(summary = "Получить проект по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Проект найден"),
            @ApiResponse(responseCode = "404", description = "Проект не найден"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к проекту")
    })
    @GetMapping("/{projectId}")
    ProjectResponse getProject(@PathVariable UUID projectId);

    @Operation(summary = "Обновить проект")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Проект обновлён"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование"),
            @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    @PutMapping("/{projectId}")
    void updateProject(
            @PathVariable UUID projectId,
            @RequestBody UpdateProjectRequest request
    );

    @Operation(summary = "Удалить проект")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Проект удалён"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProject(@PathVariable UUID projectId);

    @Operation(summary = "Получить проекты пользователя (с пагинацией)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список проектов"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @GetMapping("/my")
    PageResponse<ProjectShortResponse> getUserProjects(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(
                    sort = "project.name",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    );

    @Operation(summary = "Получить все роли которые можно назначить в проекте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Роли получены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @GetMapping("/roles")
    List<String> getRoles();
}
