package ru.itis.semesterwork.second.security.service;

import org.springframework.stereotype.Component;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

@Component
public class AccountSecurityService {
    public boolean isOwner(String username) {
        String currentUsername = SecurityContextHelper.getCurrentUsername();
        return currentUsername.equals(username);
    }
}
