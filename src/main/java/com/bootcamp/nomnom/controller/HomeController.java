package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String homePage(Model model, @AuthenticationPrincipal User user) {
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipeService.getRecipeById((long) (Math.random() * ((8 - 1) + 1)) + 1));
        recipeList.add(recipeService.getRecipeById((long) (Math.random() * ((8 - 1) + 1)) + 1));
        recipeList.add(recipeService.getRecipeById((long) (Math.random() * ((8 - 1) + 1)) + 1));
        recipeList.add(recipeService.getRecipeById((long) (Math.random() * ((8 - 1) + 1)) + 1));
        model.addAttribute("recipes", recipeList);
        model.addAttribute("user", user);
        return "home";
    }
}
