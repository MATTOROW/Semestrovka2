package ru.itis.semesterwork.second.api.rest;

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


@RequestMapping("/api/v1/accounts")
public interface AccountRestAPI {

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    AccountDetailedResponse getCurrentAccount();

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponse findByUsername(@PathVariable("username") String username);

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    CustomPageResponseDto<AccountResponse> findAllByContainsUsernameIgnoreCase(
            @RequestParam("username") String usernamePart,
            @PageableDefault(
                    sort = "username",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    );

    @PatchMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void patchByUsername(
            @PathVariable("username") String username,
            @RequestBody AccountUpdateRequest accountUpdateRequest
    );

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    String create(@RequestBody RegistrationRequest request);

    @DeleteMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@accountSecurityService.isOwner(#username)")
    void deleteByUsername(
            @PathVariable("username") String username,
            HttpServletRequest request,
            HttpServletResponse response
    );
}
