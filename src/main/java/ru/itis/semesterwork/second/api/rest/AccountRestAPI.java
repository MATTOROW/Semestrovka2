package ru.itis.semesterwork.second.api.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;

import java.util.List;


@RequestMapping("/api/v1/accounts")
public interface AccountRestAPI {

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    AccountDetailedResponse getCurrentAccount();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AccountResponse> getAll();

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponse findByUsername(@PathVariable("username") String username);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    String create(@RequestPart RegistrationRequest request);

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void updateByUsername(
            @PathVariable("username") String username,
            @RequestBody AccountUpdateRequest request
    );

    @PatchMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void patchByUsername(
            @PathVariable("username") String username,
            @RequestBody AccountUpdateRequest request
    );

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void deleteByUsername(@PathVariable("username") String username);

    @GetMapping("/{username}/projects")
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponse> getAllAccountProjects(@PathVariable("username") String username);
}
