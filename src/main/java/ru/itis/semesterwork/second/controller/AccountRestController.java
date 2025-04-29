package ru.itis.semesterwork.second.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.AccountRestAPI;
import ru.itis.semesterwork.second.dto.request.AccountRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountRestController implements AccountRestAPI {

    private final AccountService accountService;

    @Override
    public AccountDetailedResponse getCurrentAccount() {
        return null;
    }

    @Override
    public AccountResponse findByUsername(String username) {
        return accountService.findByUsername(username);
    }

    @Override
    public UUID create(AccountRequest accountRequest) {
        return accountService.create(accountRequest);
    }

    @Override
    public void updateByUsername(String username, AccountRequest accountRequest) {
        accountService.updateByUsername(username, accountRequest);
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
