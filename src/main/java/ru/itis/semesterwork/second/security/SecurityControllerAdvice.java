package ru.itis.semesterwork.second.security;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ModelAttribute("currentUser")
    public UserDetailsImpl getCurrentUser() {
        return SecurityContextHelper.getCurrentUser();
    }
}