package com.hexora.hexora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping({"/logout", "/recuperar-contrasena"})
    public String volverAlLogin() {
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        // No hay login real, se redirige directamente al dashboard.
        return "redirect:/dashboard";
    }

}

