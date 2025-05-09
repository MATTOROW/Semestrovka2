package ru.itis.semesterwork.second.util;

import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.semesterwork.second.security.UserDetailsImpl;


@Component
public class SecurityContextHelper {

    private static UserDetailsService userDetailsService;

    private final UserDetailsService injectedUserDetailsService;

    public SecurityContextHelper(UserDetailsService injectedUserDetailsService) {
        this.injectedUserDetailsService = injectedUserDetailsService;
    }

    @PostConstruct
    private void init() {
        SecurityContextHelper.userDetailsService = this.injectedUserDetailsService;
    }

    public static UserDetailsImpl getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
            return null;
        }
        return (UserDetailsImpl) auth.getPrincipal();
    }

    public static String getCurrentUsername() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getUsername();
        }
        return null;
    }

    public static String getCurrentUserPassword() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getPassword();
        }
        return null;
    }

    public static void updateCurrentUser(String username) {
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(username);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
