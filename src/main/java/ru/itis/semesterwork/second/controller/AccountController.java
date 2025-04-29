package ru.itis.semesterwork.second.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.service.AccountService;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping({"", "/{username}"})
    @ResponseStatus(HttpStatus.OK)
    public String viewAccount(Authentication auth, Model model, @PathVariable(required = false) String username) {
        String currentUsername = ((UserDetails) auth.getPrincipal()).getUsername();

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
        String currentUsername = ((UserDetails) auth.getPrincipal()).getUsername();
        AccountDetailedResponse account = accountService.findDetailedByUsername(currentUsername);
        model.addAttribute("account", account);
//        model.addAttribute("passwordForm", new PasswordChangeForm());
        return "account-edit";
    }
}
