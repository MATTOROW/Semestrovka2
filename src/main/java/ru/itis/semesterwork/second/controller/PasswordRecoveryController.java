package ru.itis.semesterwork.second.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class PasswordRecoveryController {
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "reset-password";
    }
}
