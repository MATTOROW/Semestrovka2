package ru.itis.semesterwork.second.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semesterwork.second.api.rest.AccountRestAPI;
import ru.itis.semesterwork.second.dto.request.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.service.AccountService;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountRestController implements AccountRestAPI {

    private final AccountService accountService;

    @Override
    public AccountDetailedResponse getCurrentAccount() {
        return accountService.findDetailedByUsername(SecurityContextHelper.getCurrentUsername());
    }

    @Override
    public List<AccountResponse> getAll() {
        return accountService.getAll();
    }

    @Override
    public AccountResponse findByUsername(String username) {
        return accountService.findByUsername(username);
    }


    @Override
    public String createJson(RegistrationRequest accountRequest) {
        return accountService.create(accountRequest, null);
    }

    @Override
    public String createMultipart(RegistrationRequest data, MultipartFile icon) {
        return accountService.create(data, icon);
    }

    @Override
    public void updateByUsername(String username, AccountUpdateRequest data) {
        accountService.updateByUsername(username, data);
    }

    @Override
    public void patchByUsername(String username, AccountUpdateRequest data) {
        accountService.patchByUsername(username, data);
    }

    @Override
    public void deleteByUsername(String username) {
        accountService.deleteByUsername(username);
    }

    @Override
    public List<ProjectResponse> getAllAccountProjects(String username) {
        return accountService.getAllAccountProjects(username);
    }
}
