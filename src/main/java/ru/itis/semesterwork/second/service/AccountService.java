package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.itis.semesterwork.second.dto.request.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.exception.CustomAccountNotFoundServiceException;
import ru.itis.semesterwork.second.exception.UsernameConflictException;
import ru.itis.semesterwork.second.exception.UsernameOrEmailConflictException;
import ru.itis.semesterwork.second.mapper.AccountMapper;
import ru.itis.semesterwork.second.model.AccountEntity;
import ru.itis.semesterwork.second.repository.AccountRepository;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

@Service
@RequiredArgsConstructor
@Validated
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final IconStorageService iconStorageService;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;

    public AccountResponse findByUsername(String username) {
        return accountMapper.toResponse(
                accountRepository.findByUsername(username)
                        .orElseThrow(() -> new CustomAccountNotFoundServiceException(username))
        );
    }

    public AccountDetailedResponse findDetailedByUsername(String username) {
        return accountMapper.toDetailedResponse(
                accountRepository.findByUsername(username)
                        .orElseThrow(() -> new CustomAccountNotFoundServiceException(username))
        );
    }

    @Transactional
    public String create(@Valid RegistrationRequest accountRequest) {
        if (accountRepository.existsByUsernameOrEmail(accountRequest.username(), accountRequest.email())) {
            throw new UsernameOrEmailConflictException(accountRequest.username(), accountRequest.email());
        }

        AccountEntity accountEntity = accountMapper.toEntity(accountRequest);
        accountEntity.setHashed_password(passwordEncoder.encode(accountRequest.password()));

        return accountRepository.save(accountEntity).getUsername();
    }

    public void patchByUsername(String username, AccountUpdateRequest accountUpdateRequest) {
        AccountEntity account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new CustomAccountNotFoundServiceException(username));

        if (
                accountUpdateRequest.username().isPresent() &&
                !username.equals(accountUpdateRequest.username().get()) &&
                accountRepository.existsByUsername(accountUpdateRequest.username().get())
        ) {
            throw new UsernameConflictException(accountUpdateRequest.username().get());
        }

        accountMapper.updateAccountEntity(accountUpdateRequest, account);
        AccountEntity updatedAccount = accountRepository.save(account);
        if (!account.equals(updatedAccount)) {
            SecurityContextHelper.updateCurrentUser(updatedAccount.getUsername());
        }
    }

    @Transactional
    public void deleteByUsername(String username) {
        iconStorageService.deleteIcon(accountRepository.getIconUrlByUsername(username));
        accountRepository.deleteByUsername(username);
        projectService.deleteAllWhereAccountOwner(username);
        projectMemberService.deleteAllByAccountUsername(username);
    }

    public Page<AccountResponse> findAllByContainsUsernameIgnoreCase(String usernamePart, Pageable pageable) {
        return accountRepository.findAllByUsernameContainsIgnoreCase(usernamePart, pageable)
                .map(accountMapper::toResponse);
    }
}
