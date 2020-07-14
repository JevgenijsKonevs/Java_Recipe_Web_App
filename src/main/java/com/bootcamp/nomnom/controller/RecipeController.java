package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.repository.RecipeRepository;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.util.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    // to create directory for photos, need to add lines to NomnomApplication.java before run()
    // new File(RecipeController.dir).mkdir(); - resources folder
    // new File(RecipeController.dir + "/recipe-photos").mkdir(); - subfolder for recipe photos
    // new File(RecipeController.dir + "/user-photos").mkdir(); - subfolder for user photos
    static String dir = RecipeController.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "../resources";

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    /* CREATE RECIPE */
    @GetMapping("/new")
    public String createRecipe(Model model) {
        model.addAttribute("recipes", new Recipe());
        // return thymeleaf template with creation form
        return "";
    }

    @PostMapping("")
    public String postRecipe(Recipe recipe, @RequestParam("file") MultipartFile file) {
        // need to update Recipe entity - add filename column

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = StringGenerator.getRandomString() + "." + extension;

        if (extension != "jpg" || extension != "jpeg" || extension != "png") {
            // invalid extension exception
            return "";
        } else {
            Path fileNameAndPath = Paths.get(dir + "/recipe-photo/" + filename);

            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                // needs proper handling
                e.printStackTrace();
            }

            // add filename to Recipe object
            // recipe.setFilename(filename)

            try {
                recipeRepository.save(recipe);
            } catch (Exception e) {
                // needs proper handling
                e.printStackTrace();
            }
            // redirect to recipe list view
            return "redirect:/";
        }
    }

    /* UPDATE RECIPE */


    /* DELETE RECIPE */
    
}
