package com.example.inobao.domain.user.controller;

import com.example.inobao.domain.user.dto.UserRequestDto;
import com.example.inobao.global.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/auth/login-page")
    public String loginPage() {
        return "login.html";
    }
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }
    @GetMapping("/auth/signup")
    public String signupPage() {
        return "signup.html";
    }

}