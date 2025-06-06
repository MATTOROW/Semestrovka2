package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.AddMemberRequest;
import ru.itis.semesterwork.second.dto.request.UpdateMemberRoleRequest;
import ru.itis.semesterwork.second.dto.response.ProjectMemberResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/projects/{projectId}/members")
public interface ProjectMemberRestAPI {

    @Operation(summary = "Отправить приглашение в проект")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Приглашение отправлено"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на добавление"),
            @ApiResponse(responseCode = "404", description = "Проект или аккаунт не найден"),
            @ApiResponse(responseCode = "409", description = "Участник уже в проекте")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void addMember(
            @PathVariable UUID projectId,
            @RequestBody AddMemberRequest request
    );

    @Operation(summary = "Изменить роль участника")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Роль обновлена"),
            @ApiResponse(responseCode = "400", description = "Невалидная роль"),
            @ApiResponse(responseCode = "403", description = "Нет прав на изменение"),
            @ApiResponse(responseCode = "404", description = "Проект или участник не найден")
    })
    @PutMapping("/{username}")
    void updateMemberRole(
            @PathVariable UUID projectId,
            @PathVariable String username,
            @RequestBody UpdateMemberRoleRequest request
    );

    @Operation(summary = "Удалить участника из проекта")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Участник удалён"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Проект или участник не найден"),
            @ApiResponse(responseCode = "409", description = "Владелец не может удалить самого себя.")
    })
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeMember(
            @PathVariable UUID projectId,
            @PathVariable String username
    );

    @Operation(summary = "Получить участников проекта")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список участников"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к проекту"),
            @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    @GetMapping
    List<ProjectMemberResponse> getProjectMembers(@PathVariable UUID projectId);

    @Operation(summary = "Выйти из проекта")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Успешный выход"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к проекту"),
            @ApiResponse(responseCode = "404", description = "Проект не найден"),
            @ApiResponse(responseCode = "409", description = """
                    Пользователь владелец проекта, он не может его покинуть. Либо передает права, либо удаляет проект"""
            )
    })
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void exitProject(@PathVariable UUID projectId);

    @Hidden
    @Operation(summary = "Узнать роль пользователя в проекте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Роль получена",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к проекту"),
            @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    @GetMapping("/{username}/role")
    String getRoleInProject(
            @PathVariable UUID projectId,
            @PathVariable String username
    );
}
