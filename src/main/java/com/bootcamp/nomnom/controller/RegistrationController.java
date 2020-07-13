package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String registerPage() {
        return "register";
    }

    @PostMapping
    public String addUser(@ModelAttribute User user) {
        userService.saveUserRegister(user);
        return "redirect:/login";
    }

}
