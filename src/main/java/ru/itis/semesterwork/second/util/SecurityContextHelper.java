package ru.itis.semesterwork.second.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.itis.semesterwork.second.security.UserDetailsImpl;

public class SecurityContextHelper {

    public static UserDetailsImpl getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }
        return (UserDetailsImpl) auth.getPrincipal();
    }

    public static String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }

    public static String getCurrentUserPassword() {
        return getCurrentUser().getPassword();
    }
}
