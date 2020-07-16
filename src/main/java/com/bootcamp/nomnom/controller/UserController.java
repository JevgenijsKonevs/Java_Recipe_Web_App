package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String findUserProfile(@PathVariable("username") String username) {
        //TODO: if the username the user is looking for matches their own username, redirect to profile page..
        if (username.equals("currently logged in username")) {
            return "profile";
        }
        return "user-page";
    }

    @PostMapping()
    public String postImage(@ModelAttribute User user, @RequestParam("file") MultipartFile file) throws IOException {
        userService.saveProfilePhoto(user,file);
        return "";
    }


}
