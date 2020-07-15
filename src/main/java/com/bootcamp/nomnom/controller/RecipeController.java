package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.RecipeRepository;
import com.bootcamp.nomnom.util.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    public static String dir = RecipeController.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "../resources";

    @Autowired
    private RecipeRepository recipeRepository;

    /* service methods */
    @GetMapping()
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
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

        if(ImageIO.read(file.getInputStream()) != null) {
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
            return "redirect:/recipe";

        } else {
            return "new-recipe";
        }

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
