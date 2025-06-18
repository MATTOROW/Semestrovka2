package ru.itis.semesterwork.second.service;

import io.minio.errors.MinioException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.itis.semesterwork.second.dto.request.account.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.auth.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.account.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.account.AccountResponse;
import ru.itis.semesterwork.second.exception.AvatarNotFoundException;
import ru.itis.semesterwork.second.exception.CustomAccountNotFoundServiceException;
import ru.itis.semesterwork.second.exception.UsernameConflictException;
import ru.itis.semesterwork.second.exception.UsernameOrEmailConflictException;
import ru.itis.semesterwork.second.mapper.AccountMapper;
import ru.itis.semesterwork.second.model.AccountEntity;
import ru.itis.semesterwork.second.repository.AccountRepository;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

import javax.security.auth.login.AccountNotFoundException;


@Service
@RequiredArgsConstructor
@Validated
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final MinioService minioService;
    private final SessionRegistry sessionRegistry;

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

    public void patchByUsername(String username, @Valid AccountUpdateRequest accountUpdateRequest) {
        AccountEntity account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new CustomAccountNotFoundServiceException(username));

        if (
                accountUpdateRequest.username().isPresent() &&
                !username.equals(accountUpdateRequest.username().value()) &&
                accountRepository.existsByUsername(accountUpdateRequest.username().value())
        ) {
            throw new UsernameConflictException(accountUpdateRequest.username().value());
        }

        String oldUsername = account.getUsername();
        String oldDescription = account.getAccountInfoEntity().getDescription();

        accountMapper.updateAccountEntity(accountUpdateRequest, account);

        AccountEntity updatedAccount = accountRepository.save(account);

        String newUsername = updatedAccount.getUsername();
        String newDescription = updatedAccount.getAccountInfoEntity().getDescription();


        if (
                !oldUsername.equals(newUsername) ||
                        (
                                oldDescription != null &&
                                !oldDescription.equals(newDescription)
                        ) || (oldDescription == null && newDescription != null)
        ) {
            SecurityContextHelper.updateCurrentUser(updatedAccount.getUsername());
        }
    }

    @Transactional
    public void deleteByUsername(String username) {
        try {
            minioService.deleteAvatar();
        } catch (AvatarNotFoundException ignored) {}
        accountRepository.deleteByUsername(username);
        projectService.deleteAllWhereAccountOwner(username);
        projectMemberService.deleteAllByAccountUsername(username);

        sessionRegistry.getAllPrincipals().stream()
                .filter(p -> ((AccountEntity) p).getUsername().equals(username))
                .findFirst()
                .ifPresent(principal -> {
                    sessionRegistry.getAllSessions(principal, false)
                            .forEach(SessionInformation::expireNow);
                });
    }

    public Page<AccountResponse> findAllByContainsUsernameIgnoreCase(String usernamePart, Pageable pageable) {
        return accountRepository.findAllByUsernameContainsIgnoreCase(usernamePart, pageable)
                .map(accountMapper::toResponse);
    }
}
