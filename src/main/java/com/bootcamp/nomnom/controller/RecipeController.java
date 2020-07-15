package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.RecipeRepository;
import com.bootcamp.nomnom.util.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    public static String dir = RecipeController.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "../resources";

    @Autowired
    RecipeRepository recipeRepository;

    /* service methods */
    @GetMapping("/")
    public String getAllRecipes() {
        return "recipes";
    }

    @GetMapping("/{recipeId}")
    public String recipePage() {
        return "recipe-page";
    }

    @GetMapping("/new")
    public String createRecipe() {
        return "new-recipe";
    }

    @PostMapping()
    public String postRecipe(@ModelAttribute Recipe recipe, @AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {

        String fileName = StringGenerator.getRandomFilename(file);
        Path filePath = Paths.get(dir + "/recipe-photos/" + fileName);

        try {
            Files.write(filePath, file.getBytes());
            recipe.setFileName(fileName);
            recipe.setUser(user);
            recipeRepository.save(recipe);
        } catch (Exception e) {
            // need proper handling...
            e.printStackTrace();
            return "new-recipe";
        }

        // need to redirect to recipe page...
        return "redirect:/";
    }

    @PostMapping("/update/{recipeId}")
    // @pathvariable("recipeId")
    public String updateRecipe() {

        return "redirect:/";
    }

    @GetMapping("/delete/{recipeId}")
    // @pathvariable("recipeId")
    public String deleteRecipe() {
        return "redirect:/";
    }

    @PostMapping("/{recipeId}/comment")
    public String addComment() {
        return "";
    }

    @GetMapping("/{recipeId}/comment/delete/{commentId}")
    public String deleteComment() {
        return "";
    }

    @PostMapping("/{recipeId}/like/{likeValue}")
    public String submitLike() {
        return "";
    }
}
