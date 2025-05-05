package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    String createJson(@RequestBody RegistrationRequest request);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    String createMultipart(@RequestPart RegistrationRequest data, @RequestPart(required = false) MultipartFile icon);

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void updateByUsername(
            @PathVariable("username") String username,
            @RequestBody AccountUpdateRequest data
    );

    @PatchMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void patchByUsername(
            @PathVariable("username") String username,
            @RequestBody AccountUpdateRequest data
    );

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    void deleteByUsername(@PathVariable("username") String username);

    @GetMapping("/{username}/projects")
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponse> getAllAccountProjects(@PathVariable("username") String username);
}
