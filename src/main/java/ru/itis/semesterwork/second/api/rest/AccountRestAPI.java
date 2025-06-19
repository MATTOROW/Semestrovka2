package ru.itis.semesterwork.second.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.account.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.auth.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.account.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.account.AccountResponse;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;


@Tag(name = "Account Management", description = "API для управления аккаунтами")
@RequestMapping("/api/v1/accounts")
public interface AccountRestAPI {

    @Operation(summary = "Получить текущий аккаунт", description = "Возвращает информацию о текущем аутентифицированном пользователе")
    @ApiResponse(responseCode = "200", description = "Аккаунт найден",
            content = @Content(schema = @Schema(implementation = AccountDetailedResponse.class)))
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    AccountDetailedResponse getCurrentAccount();

    @Operation(summary = "Найти аккаунт по username", description = "Возвращает базовую информацию об аккаунте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Аккаунт найден",
                    content = @Content(schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    })
    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponse findByUsername(@PathVariable("username") String username);

    @Operation(summary = "Поиск аккаунтов по username", description = "Возвращает страницу аккаунтов, содержащих указанную строку в username")
    @ApiResponse(responseCode = "200", description = "Список аккаунтов",
            content = @Content(schema = @Schema(implementation = CustomPageResponseDto.class)))
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    CustomPageResponseDto<AccountResponse> findAllByContainsUsernameIgnoreCase(
            @RequestParam("username") String usernamePart,
            @PageableDefault(
                    sort = "username",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    );

    @Operation(summary = "Обновить аккаунт", description = "Частично обновляет информацию об аккаунте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Аккаунт обновлен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Нет прав на обновление"),
            @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    })
    @PatchMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void patchByUsername(
            @PathVariable("username") String username,
            @RequestBody AccountUpdateRequest accountUpdateRequest
    );

    @Operation(summary = "Создать аккаунт", description = "Регистрирует нового пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Аккаунт создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "409", description = "Аккаунт с таким username или email уже существует")
    })
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    String create(@RequestBody RegistrationRequest request);

    @Operation(summary = "Удалить аккаунт", description = "Удаляет аккаунт по username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Аккаунт удален"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление"),
            @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    })
    @DeleteMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@accountSecurityService.isOwner(#username)")
    void deleteByUsername(
            @PathVariable("username") String username,
            HttpServletRequest request,
            HttpServletResponse response
    );
}
