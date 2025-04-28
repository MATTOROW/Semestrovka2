package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.AccountRequest;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/accounts")
public interface AccountRestAPI {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AccountResponse> getAll();

    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponse findByInnerId(@PathVariable("innerId") UUID accountInnerId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody AccountRequest accountRequest);

    @PutMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void updateByInnerId(@PathVariable("innerId") UUID accountInnerId, @RequestBody AccountRequest accountRequest);

    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteByInnerId(@PathVariable("innerId") UUID accountInnerId);

    @GetMapping("/{accountInnerId}/projects")
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponse> getAllAccountProjects(@PathVariable("accountInnerId") UUID accountInnerId);
}
