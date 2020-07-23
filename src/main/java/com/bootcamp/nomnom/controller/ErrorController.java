package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.User;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "sad-cupcake-error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
