package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.RecipeService;
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

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/profile")
    public String showProfilePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("recipes", recipeService.getAllRecipeByUser(user.getId()));
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/{username}")
    public String findUserProfile(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        if (user != null && username.equals(user.getUsername())) {
            return "profile";
        }
        return "user-page";
    }

    @PostMapping("/update/image")
    public String postImage(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user, @RequestParam("button") String command) throws IOException {
        if(command.equals("update")) {
            userService.saveProfilePhoto(user, file);
        } else {
            userService.deleteUserPicture(user);
        }

        return "redirect:/user/profile";
    }

    @PostMapping("/update/password")
    public String changeUserPassword(@AuthenticationPrincipal User user, @RequestParam String password ) {
        userService.updateUserPassword(user, password);
        return "redirect:/user/profile";
    }

}
