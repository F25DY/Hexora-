package com.hexora.hexora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        // Placeholder: accept any credentials for UI/demo purposes and redirect to dashboard.
        // Replace with real authentication logic as needed.
        model.addAttribute("usuario", new Object());
        return "redirect:/dashboard";
    }

}
