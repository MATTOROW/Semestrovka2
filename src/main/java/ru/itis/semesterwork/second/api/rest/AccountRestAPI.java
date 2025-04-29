package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.AccountRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/accounts")
public interface AccountRestAPI {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    AccountDetailedResponse getCurrentAccount();

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponse findByUsername(@PathVariable("username") String username);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody AccountRequest accountRequest);

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void updateByUsername(@PathVariable("username") String username, @RequestBody AccountRequest accountRequest);

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void deleteByUsername(@PathVariable("username") String username);

    @GetMapping("/{username}/projects")
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponse> getAllAccountProjects(@PathVariable("username") String username);
}
