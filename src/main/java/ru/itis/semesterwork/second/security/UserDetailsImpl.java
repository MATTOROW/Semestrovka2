package ru.itis.semesterwork.second.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.semesterwork.second.model.AccountEntity;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final AccountEntity account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return account.getHashed_password();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    public String getEmail() {
        return account.getEmail();
    }

    public String getDescription() {
        return account.getAccountInfoEntity().getDescription();
    }
}
