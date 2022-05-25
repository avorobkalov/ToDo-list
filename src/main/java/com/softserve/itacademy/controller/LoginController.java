package com.softserve.itacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"/login-form", "login"})
    public String login() {
        return "login-form";
    }
}
