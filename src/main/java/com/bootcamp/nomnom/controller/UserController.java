package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        return "profile";
    }

    @GetMapping("/{username}")
    public String findUserProfile(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        if (user != null && username.equals(user.getUsername())) {
            return "profile";
        }
        return "user-page";
    }

    @PostMapping()
    public String postImage(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {

        userService.saveProfilePhoto(user, file);
        return "redirect:/user/profile";
    }


}
