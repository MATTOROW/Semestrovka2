package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semesterwork.second.dto.request.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;
import ru.itis.semesterwork.second.exception.CustomAccountNotFoundServiceException;
import ru.itis.semesterwork.second.exception.UsernameConflictException;
import ru.itis.semesterwork.second.exception.UsernameOrEmailConflictException;
import ru.itis.semesterwork.second.mapper.AccountMapper;
import ru.itis.semesterwork.second.mapper.ProjectMapper;
import ru.itis.semesterwork.second.model.AccountEntity;
import ru.itis.semesterwork.second.repository.AccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ProjectMapper projectMapper;
    private final PasswordEncoder passwordEncoder;
    private final IconStorageService iconStorageService;

    public List<AccountResponse> getAll() {
        return accountRepository.findAll().stream().map(accountMapper::toResponse).toList();
    }

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
    public String create(@Valid RegistrationRequest accountRequest, MultipartFile image) {
        if (accountRepository.existsByUsernameOrEmail(accountRequest.username(), accountRequest.email())) {
            throw new UsernameOrEmailConflictException(accountRequest.username(), accountRequest.email());
        }

        AccountEntity accountEntity = accountMapper.toEntity(accountRequest);
        accountEntity.setHashed_password(passwordEncoder.encode(accountRequest.password()));

        if (image != null) {
            accountEntity.getAccountInfoEntity().setIconUrl(
                    iconStorageService.store(image, accountRequest.username())
            );
        }

        return accountRepository.save(accountEntity).getUsername();
    }

    @Transactional
    public void updateByUsername(
            String username,
            @Valid AccountUpdateRequest accountRequest,
            MultipartFile image
    ) {
        AccountEntity accountEntity = accountRepository.findByUsername(username).orElseThrow(
                () -> new CustomAccountNotFoundServiceException(username)
        );

        if (!username.equals(accountRequest.username())) {
            if (accountRepository.existsByUsername(accountRequest.username())) {
                throw new UsernameConflictException(accountRequest.username());
            }
            accountEntity.setUsername(accountRequest.username());
        }

        accountEntity.getAccountInfoEntity().setDescription(accountRequest.description());

        if (image != null && image.getSize() != 0) {
            iconStorageService.deleteIcon(accountEntity.getAccountInfoEntity().getIconUrl());
            accountEntity.getAccountInfoEntity().setIconUrl(
                    iconStorageService.store(image, accountRequest.username())
            );
        } else {
            if (accountRequest.deleteIcon()) {
                iconStorageService.deleteIcon(accountEntity.getAccountInfoEntity().getIconUrl());
                accountEntity.getAccountInfoEntity().setIconUrl(null);
            } else {
                if (!username.equals(accountRequest.username())) {
                    accountEntity.getAccountInfoEntity().setIconUrl(
                            iconStorageService.renameIcon(
                                    accountEntity.getAccountInfoEntity().getIconUrl(),
                                    accountRequest.username()
                            )
                    );
                }
            }
        }

        accountRepository.flush();
    }


    @Transactional
    public void patchByUsername(
            String username,
            @Valid AccountUpdateRequest accountRequest,
            MultipartFile image
    ) {

    }

    @Transactional
    public void deleteByUsername(String username) {
        iconStorageService.deleteIcon(accountRepository.getIconUrlByUsername(username));
        accountRepository.deleteByUsername(username);
    }

    public List<ProjectResponse> getAllAccountProjects(String username) {
        AccountEntity accountEntity = accountRepository.findByUsername(username).orElseThrow(
                () -> new CustomAccountNotFoundServiceException(username)
        );

        return accountEntity.getProjects().stream()
                .map(member -> projectMapper.toResponse(member.getProject()))
                .toList();
    }
}
