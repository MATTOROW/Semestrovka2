package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    String createJson(@RequestBody RegistrationRequest request);

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    String createMultipart(@RequestPart RegistrationRequest data, @RequestPart(required = false) MultipartFile icon);

    @PutMapping(path = "/update/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@accountSecurityService.isOwner(#username)")
    void updateByUsernameMultipart(
            @PathVariable("username") String username,
            @RequestPart AccountUpdateRequest data,
            @RequestPart(required = false) MultipartFile icon
    );

    // На данный момент нет необходимости в данном функционале.
//    @PatchMapping(path = "/patch/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("@accountSecurityService.isOwner(#username)")
//    void patchByUsernameJson(
//            @PathVariable("username") String username,
//            @RequestBody AccountUpdateRequest data
//    );
//
//    @PatchMapping(path = "/patch/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("@accountSecurityService.isOwner(#username)")
//    void patchByUsernameMultipart(
//            @PathVariable("username") String username,
//            @RequestPart AccountUpdateRequest data,
//            @RequestPart(required = false) MultipartFile icon
//    );

    @DeleteMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@accountSecurityService.isOwner(#username)")
    void deleteByUsername(@PathVariable("username") String username);

    @GetMapping("/{username}/projects")
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponse> getAllAccountProjects(@PathVariable("username") String username);
}
