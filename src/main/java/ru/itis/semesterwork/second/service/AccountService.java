package ru.itis.semesterwork.second.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.dto.request.AccountRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.mapper.AccountMapper;
import ru.itis.semesterwork.second.repository.AccountRepository;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public List<AccountResponse> getAll() {
        return accountRepository.findAll().stream().map(accountMapper::toResponse).toList();
    }

    public AccountResponse findByUsername(String username) {
        return accountMapper.toResponse(
                accountRepository.findByUsername(username).orElseThrow(RuntimeException::new)
        );
    }

    public AccountDetailedResponse findDetailedByUsername(String username) {
        return accountMapper.toDetailedResponse(
                accountRepository.findByUsername(username).orElseThrow(RuntimeException::new)
        );
    }

    public UUID create(AccountRequest accountRequest) {
        return null;
    }

    public void updateByUsername(String username, AccountRequest accountRequest) {
    }

    public void deleteByUsername(String username) {
    }

    public List<ProjectResponse> getAllAccountProjects(String username) {
        return null;
    }
}
