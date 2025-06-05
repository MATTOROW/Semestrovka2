package ru.itis.semesterwork.second.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semesterwork.second.api.rest.AccountRestAPI;
import ru.itis.semesterwork.second.dto.request.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.CustomPageResponseDto;
import ru.itis.semesterwork.second.service.AccountService;
import ru.itis.semesterwork.second.util.SecurityContextHelper;


@RestController
@RequiredArgsConstructor
public class AccountRestController implements AccountRestAPI {

    private final AccountService accountService;

    @Override
    public AccountDetailedResponse getCurrentAccount() {
        return accountService.findDetailedByUsername(SecurityContextHelper.getCurrentUsername());
    }

    @Override
    public CustomPageResponseDto<AccountResponse> findAllByContainsUsernameIgnoreCase(String usernamePart, Pageable pageable) {
        Page<AccountResponse> accounts = accountService.findAllByContainsUsernameIgnoreCase(usernamePart, pageable);
        return new CustomPageResponseDto<>(
                accounts.getNumber(),
                accounts.getSize(),
                accounts.getTotalElements(),
                accounts.getTotalPages(),
                accounts.getContent()
        );
    }

    @Override
    public void patchByUsername(String username, AccountUpdateRequest accountUpdateRequest) {
        accountService.patchByUsername(username, accountUpdateRequest);
    }

    @Override
    public AccountResponse findByUsername(String username) {
        return accountService.findByUsername(username);
    }


    @Override
    public String create(RegistrationRequest accountRequest) {
        return accountService.create(accountRequest);
    }

    @Override
    public void deleteByUsername(String username) {
        accountService.deleteByUsername(username);
    }
}
