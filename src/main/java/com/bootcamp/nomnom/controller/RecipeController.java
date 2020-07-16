package com.bootcamp.nomnom.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.bootcamp.nomnom.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.RecipeRepository;
import com.bootcamp.nomnom.util.StringGenerator;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping()
    public String getAllRecipes(Model model) {
        return "recipes";
    }

    @GetMapping("/{recipeId}")
    public String recipePage(@PathVariable("recipeId") Long recipeId) {
        return "recipe-page";
    }

    @GetMapping("/new")
    public String createRecipe() {
        return "new-recipe";
    }

    @PostMapping()
    public String postRecipe(@ModelAttribute Recipe recipe, @AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) throws IOException {
        recipeService.saveRecipe(recipe,user,file);
        return "";
    }

    @PostMapping("/update/{recipeId}")
    public String updateRecipe(@PathVariable("recipeId") Long recipeId) {
        return "redirect:/";
    }

    @GetMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") Long recipeId) {
        return "redirect:/";
    }

    @PostMapping("/{recipeId}/comment")
    public String addComment(@PathVariable("recipeId") Long recipeId) {
        return "";
    }

    @GetMapping("/{recipeId}/comment/delete/{commentId}")
    public String deleteComment(@PathVariable("recipeId") Long recipeId, @PathVariable("commentId") Long commentId) {
        return "";
    }

    @PostMapping("/{recipeId}/like/{likeValue}")
    public String submitLike(@PathVariable("recipeId") Long recipeId, @PathVariable("likeValue") boolean recipeLike) {
        return "";
    }
}
