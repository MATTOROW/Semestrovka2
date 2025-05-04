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

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping({"", "/{username}"})
    @ResponseStatus(HttpStatus.OK)
    public String viewAccount(Authentication auth, Model model, @PathVariable(required = false) String username) {
        String currentUsername = ((UserDetailsImpl) auth.getPrincipal()).getUsername();

        boolean isOwner = username == null || username.equals(currentUsername);
        String accountUsername = isOwner ? currentUsername : username;

        AccountDetailedResponse account = accountService.findDetailedByUsername(accountUsername);
        model.addAttribute("account", account);
        model.addAttribute("isOwner", isOwner);
        return "account";
    }

    @GetMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public String editForm(Authentication auth, Model model) {
        String currentUsername = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
        AccountDetailedResponse account = accountService.findDetailedByUsername(currentUsername);
        model.addAttribute("account", account);
        return "account-edit";
    }
}
