package ru.itis.semesterwork.second.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.security.UserDetailsImpl;
import ru.itis.semesterwork.second.service.AccountService;
import ru.itis.semesterwork.second.util.SecurityContextHelper;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping({"/view", "/{username}"})
    @ResponseStatus(HttpStatus.OK)
    public String viewAccount(Model model, @PathVariable(required = false) String username) {
        String currentUsername = SecurityContextHelper.getCurrentUsername();

        boolean isOwner = username == null || username.equals(currentUsername);
        if (isOwner) {
            model.addAttribute("account", SecurityContextHelper.getCurrentUser());
        } else {
            AccountDetailedResponse account = accountService.findDetailedByUsername(username);
            model.addAttribute("account", account);
        }

        model.addAttribute("isOwner", isOwner);
        return "account";
    }

    @GetMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public String editForm(Model model) {
        model.addAttribute("account", SecurityContextHelper.getCurrentUser());
        return "account-edit";
    }
}
