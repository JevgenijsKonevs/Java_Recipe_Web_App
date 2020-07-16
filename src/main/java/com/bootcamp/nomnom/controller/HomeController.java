package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String homePage(Model model, @AuthenticationPrincipal User user) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "home";
    }
}
